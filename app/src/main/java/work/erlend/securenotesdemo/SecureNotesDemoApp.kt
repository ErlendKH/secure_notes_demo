package work.erlend.securenotesdemo

// Uses CryptoManager solution.
/*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import work.erlend.securenotesdemo.data.local.NotesDatabase
import work.erlend.securenotesdemo.navigation.MainScreen

@Composable
fun SecureNotesDemoApp(database: NotesDatabase) {
    MaterialTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AgileInfoCarousel()
//        }
        MainScreen(database)
    }
}
*/

// Uses CryptoManager solution.
/*
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import work.erlend.securenotesdemo.data.PassphraseManager
import work.erlend.securenotesdemo.data.local.NotesDatabase
import work.erlend.securenotesdemo.ui.notes.NotesScreen

@Composable
fun SecureNotesDemoApp(context: Context) {
    val passphrase = PassphraseManager(context).getPassphrase()
    val db = NotesDatabase.getDatabase(context, passphrase)

    MaterialTheme {
        NotesScreen(noteDao = db.noteDao())
    }
}
*/

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import work.erlend.securenotesdemo.navigation.MainScreen

@Composable
fun SecureNotesDemoApp(database: NotesDatabase) {
    MaterialTheme {
        MainScreen(database = database)
    }
}