package work.erlend.securenotesdemo.ui.notes

//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun NotesScreen() {
//    // Placeholder content
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "📝 Notes Screen (Placeholder)",
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Center,
//        )
//    }
//}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import work.erlend.securenotesdemo.data.local.NotesDatabase

@Composable
fun NotesScreen(database: NotesDatabase) {
    val viewModel = remember { NotesViewModel(database) }
    val notes by viewModel.notes.collectAsState()
    var input by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Write a note...") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.addNote(input)
                input = ""
            }
        ) {
            Text("Save Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Saved Notes", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(notes) { note ->
                NoteItem(noteContent = note.content)
            }
        }
    }
}