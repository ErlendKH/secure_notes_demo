package work.erlend.securenotesdemo.data

import android.content.Context
import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import work.erlend.securenotesdemo.data.KeystoreHelper

class SQLCipherPassphraseRepository(private val context: Context) {
    companion object {
        private const val PREFS = "secure_prefs"
        private const val KEY_ALIAS = "sqlcipher_passphrase_key_v1"
        private const val PREF_ENC = "sqlcipher_passphrase_enc"
        private const val PREF_IV = "sqlcipher_passphrase_iv"
        private const val PASS_LEN_BYTES = 32 // 256-bit random passphrase
        private const val AES_TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_TAG_BITS = 128
    }

    private val prefs by lazy { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }
    private val rng = SecureRandom()

    fun getOrCreatePassphrase(): String {
        val encB64 = prefs.getString(PREF_ENC, null)
        val ivB64  = prefs.getString(PREF_IV, null)
        val key = KeystoreHelper.getOrCreateAesKey(KEY_ALIAS)

        return if (encB64 != null && ivB64 != null) {
            val enc = Base64.decode(encB64, Base64.NO_WRAP)
            val iv  = Base64.decode(ivB64, Base64.NO_WRAP)
            val plain = decrypt(key, enc, iv)
            plain
        } else {
            val pass = newRandomPassphrase()
            val (enc, iv) = encrypt(key, pass)
            prefs.edit()
                .putString(PREF_ENC, Base64.encodeToString(enc, Base64.NO_WRAP))
                .putString(PREF_IV,  Base64.encodeToString(iv,  Base64.NO_WRAP))
                .apply()
            pass
        }
    }

    private fun newRandomPassphrase(): String {
        val bytes = ByteArray(PASS_LEN_BYTES)
        rng.nextBytes(bytes)
        // Use Base64 (URL-safe optional) so it’s printable; SQLCipher accepts strings.
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    private fun encrypt(key: SecretKey, plaintext: String): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val enc = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
        return enc to iv
    }

    private fun decrypt(key: SecretKey, ciphertext: ByteArray, iv: ByteArray): String {
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        val spec = GCMParameterSpec(GCM_TAG_BITS, iv)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        val plain = cipher.doFinal(ciphertext)
        return String(plain, Charsets.UTF_8)
    }
}