package work.erlend.securenotesdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import work.erlend.securenotesdemo.common.data.local.NoteRepository
import work.erlend.securenotesdemo.common.data.security.KeystorePassphraseManager
import work.erlend.securenotesdemo.common.data.local.NotesDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch or create passphrase securely
        val passphrase = KeystorePassphraseManager.getOrCreatePassphrase(this)

        // Initialize the database with the secure passphrase
        val db = NotesDatabase.getDatabase(this, passphrase)
        val noteRepository = NoteRepository(db.noteDao())

        setContent {
            SecureNotesDemoApp(db, noteRepository)
        }

    }
}