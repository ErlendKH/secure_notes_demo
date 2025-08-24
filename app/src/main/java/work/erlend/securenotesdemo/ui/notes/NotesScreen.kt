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

// Uses CryptoManager solution.
/*
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
*/

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import work.erlend.securenotesdemo.data.local.NoteDao
import work.erlend.securenotesdemo.data.local.NoteEntity

@Composable
fun NotesScreen(noteDao: NoteDao) {
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(listOf<NoteEntity>()) }

    LaunchedEffect(Unit) {
        notes = noteDao.getAllNotes()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Write a note") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (text.isNotBlank()) {
                scope.launch {
                    noteDao.insert(NoteEntity(content = text))
                    notes = noteDao.getAllNotes()
                    text = ""
                }
            }
        }) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes) { note ->
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}