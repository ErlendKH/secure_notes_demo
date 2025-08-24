package work.erlend.securenotesdemo.ui.notes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NoteItem(noteContent: String) {
    Text("• $noteContent")
}