/*
 * Secure Notes Demo
 * Copyright (C) 2025 Erlend H.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package work.erlend.securenotesdemo.common.data.security

import android.content.Context
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import androidx.core.content.edit
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import java.security.KeyStore
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Manages secure generation, storage, and retrieval of the database passphrase.
 *
 * Uses the Android Keystore to encrypt the passphrase and stores it in
 * [android.content.SharedPreferences]. Provides methods for creating, updating, encrypting,
 * decrypting, and rekeying the database.
 */
object KeystorePassphraseManager {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "SecureNotesDBKey"
    private const val AES_TRANSFORMATION = "AES/GCM/NoPadding"
    private const val PREFS_NAME = "secure_prefs"
    private const val PREF_KEY = "sqlcipher_passphrase"

    private var cachedPassphrase: String? = null

    /**
     * Returns the existing database passphrase, or creates a new one if not present.
     *
     * Retrieves the passphrase from SharedPreferences, decrypts it using a
     * SecretKey from the Android Keystore, and caches it in memory.
     *
     * @param context the [Context] used to access SharedPreferences
     * @return the decrypted database passphrase
     */
    fun getOrCreatePassphrase(context: Context): String {
        cachedPassphrase?.let { return it }

        val key = getOrCreateAesKey(KEY_ALIAS)
        val prefs = getPrefs(context)
        val existing = prefs.getString(PREF_KEY, null)

        if (existing != null) {
            try {
                cachedPassphrase = decrypt(existing, key)
                return cachedPassphrase!!
            } catch (e: Exception) {
                // Corrupted or invalid ciphertext -> generate new
                prefs.edit { remove(PREF_KEY) }
            }
        }

        // Generate new passphrase
        val newPass = UUID.randomUUID().toString()
        val encrypted = encrypt(newPass, key)
        prefs.edit { putString(PREF_KEY, encrypted) }
        cachedPassphrase = newPass
        return newPass
    }

    /**
     * Updates the database passphrase and stores it encrypted in SharedPreferences.
     *
     * Also updates the in-memory cached passphrase.
     *
     * @param context the [Context] used to access SharedPreferences
     * @param newPassphrase the new passphrase to store
     */
    fun updatePassphrase(context: Context, newPassphrase: String) {
        val key = getOrCreateAesKey(KEY_ALIAS)
        val encrypted = encrypt(newPassphrase, key)
        getPrefs(context).edit { putString(PREF_KEY, encrypted) }
        cachedPassphrase = newPassphrase
    }

    /** Returns the regular [android.content.SharedPreferences]
     * used to persist the encrypted passphrase.
     * */
    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Creates or retrieves a SecretKey from the Android Keystore.
     *
     * If a key with the given [alias] exists, it is returned. Otherwise, a new AES key
     * with GCM/NoPadding is generated and stored in the Keystore.
     *
     * @param alias the alias name for the key
     * @return the [SecretKey] used for encryption/decryption
     */
    fun getOrCreateAesKey(alias: String): SecretKey {
        val ks = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        (ks.getEntry(alias, null) as? KeyStore.SecretKeyEntry)?.let { return it.secretKey }

        val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val spec = android.security.keystore.KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGen.init(spec)
        return keyGen.generateKey()
    }

    /**
     * Encrypts a passphrase using the given [SecretKey].
     *
     * Prepends the IV to the ciphertext and encodes the result in Base64.
     *
     * @param passphrase the plaintext passphrase to encrypt
     * @param key the [SecretKey] used for encryption
     * @return the encrypted passphrase as a Base64 string
     */
    internal fun encrypt(passphrase: String, key: SecretKey): String {
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(passphrase.toByteArray(Charsets.UTF_8))
        // Note: java.util.Base64 works for tests without needing RobolectricTestRunner,
        // but it also requires API 26.
        return Base64.encodeToString(iv + encryptedBytes, Base64.DEFAULT)
    }

    /**
     * Decrypts a Base64-encoded passphrase using the given [SecretKey].
     *
     * Extracts the IV from the first 12 bytes and decrypts the remainder.
     *
     * @param encryptedBase64 the encrypted passphrase in Base64
     * @param key the [SecretKey] used for decryption
     * @return the decrypted passphrase
     * @throws IllegalArgumentException if the ciphertext is too short
     */
    internal fun decrypt(encryptedBase64: String, key: SecretKey): String {
        val bytes = Base64.decode(encryptedBase64, Base64.DEFAULT)
        require(bytes.size > 12) { "Ciphertext too short" } // Basic sanity check
        val iv = bytes.copyOfRange(0, 12)
        val cipherText = bytes.copyOfRange(12, bytes.size)
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        return String(cipher.doFinal(cipherText), Charsets.UTF_8)
    }

    /**
     * Rekeys the SQLCipher database with a new randomly generated passphrase.
     *
     * Uses PRAGMA rekey via a raw query, and updates the stored passphrase in
     * SharedPreferences.
     *
     * @param context the [Context] used to update the stored passphrase
     * @param database the [NotesDatabase] to rekey
     * @throws Exception if rekeying fails
     */
    fun rekeyDatabase(context: Context, database: NotesDatabase) {
        val newPass = UUID.randomUUID().toString()
        val db = database.openHelper.writableDatabase
        try {
            // Use rawQuery with PRAGMA rekey
            db.query("PRAGMA rekey = '$newPass'").use { cursor ->
                // no need to process results
                Log.d("KeystorePassphraseManager", "Database rekey successful!")
            }

            // Update stored passphrase
            updatePassphrase(context, newPass)
        } catch (e: Exception) {
            Log.e("KeystorePassphraseManager", "Failed to rekey database", e)
            throw e
        }
    }

}