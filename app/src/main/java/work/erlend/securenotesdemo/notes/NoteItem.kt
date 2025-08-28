package work.erlend.securenotesdemo.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import work.erlend.securenotesdemo.common.data.local.NoteEntity

/**
 * Represents a single note item in a list.
 *
 * Displays the note content and provides edit and delete buttons.
 *
 * @param note the [NoteEntity] to display
 * @param onDelete callback invoked when the delete button is pressed
 * @param onEdit callback invoked when the edit button is pressed
 */
@Composable
fun NoteItem(
    note: NoteEntity,
    onDelete: (NoteEntity) -> Unit, // pass note as argument
    onEdit: (NoteEntity) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = { onEdit(note) },) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit") }
                IconButton(onClick = { onDelete(note) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}