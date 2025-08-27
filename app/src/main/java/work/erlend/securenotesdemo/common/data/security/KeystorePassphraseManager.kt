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

object KeystorePassphraseManager {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "SecureNotesDBKey"
    private const val AES_TRANSFORMATION = "AES/GCM/NoPadding"
    private const val PREFS_NAME = "secure_prefs"
    private const val PREF_KEY = "sqlcipher_passphrase"

    private var cachedPassphrase: String? = null

    /** Returns the existing passphrase or creates a new one */
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

    /** Updates the passphrase and stores it persistently */
    fun updatePassphrase(context: Context, newPassphrase: String) {
        val key = getOrCreateAesKey(KEY_ALIAS)
        val encrypted = encrypt(newPassphrase, key)
        getPrefs(context).edit { putString(PREF_KEY, encrypted) }
        cachedPassphrase = newPassphrase
    }

    /** Regular SharedPreferences */
    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** Creates or retrieves a SecretKey from the Android Keystore */
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

    /** Encrypts a passphrase using the given SecretKey */
    private fun encrypt(passphrase: String, key: SecretKey): String {
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(passphrase.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encryptedBytes, Base64.DEFAULT)
    }

    /** Decrypts an encrypted passphrase using the given SecretKey */
    private fun decrypt(encryptedBase64: String, key: SecretKey): String {
        val bytes = Base64.decode(encryptedBase64, Base64.DEFAULT)
        require(bytes.size > 12) { "Ciphertext too short" } // Basic sanity check
        val iv = bytes.copyOfRange(0, 12)
        val cipherText = bytes.copyOfRange(12, bytes.size)
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        return String(cipher.doFinal(cipherText), Charsets.UTF_8)
    }

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