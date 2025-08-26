package work.erlend.securenotesdemo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import work.erlend.securenotesdemo.common.data.local.NoteRepository
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import work.erlend.securenotesdemo.navigation.MainScreen

@Composable
fun SecureNotesDemoApp(database: NotesDatabase, noteRepository: NoteRepository) {
    MaterialTheme {
        MainScreen(database = database, noteRepository = noteRepository)
    }
}