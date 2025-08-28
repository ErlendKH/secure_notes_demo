package work.erlend.securenotesdemo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import work.erlend.securenotesdemo.common.data.local.NoteRepository
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import work.erlend.securenotesdemo.navigation.MainScreen

/**
 * The root Composable for the Secure Notes Demo app.
 *
 * Applies the [MaterialTheme] and sets up the app's navigation by launching
 * the [MainScreen]. This function is responsible for passing the initialized
 * [NotesDatabase] and [NoteRepository] down to the navigation graph, ensuring
 * that all screens have access to secure storage and note operations.
 *
 * @param database the encrypted [NotesDatabase] instance used throughout the app
 * @param noteRepository the [NoteRepository] providing access to note data
 */
@Composable
fun SecureNotesDemoApp(database: NotesDatabase, noteRepository: NoteRepository) {
    MaterialTheme {
        MainScreen(database = database, noteRepository = noteRepository)
    }
}