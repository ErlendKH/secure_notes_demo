package work.erlend.securenotesdemo.data

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import androidx.core.content.edit

class PassphraseManager(private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)

    fun getPassphrase(): String {
        val saved = prefs.getString("sqlcipher_passphrase", null)
        if (!saved.isNullOrEmpty()) return saved

        val newPass = UUID.randomUUID().toString()
        prefs.edit { putString("sqlcipher_passphrase", newPass) }
        return newPass
    }
}