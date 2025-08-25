package work.erlend.securenotesdemo.notes

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

// First version with SQLCipher
/*
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
*/

/*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import work.erlend.securenotesdemo.data.local.NoteDao
import work.erlend.securenotesdemo.data.local.NoteEntity
import work.erlend.securenotesdemo.navigation.Screen

@Composable
fun NotesScreen(noteDao: NoteDao, navController: NavController) {
    val scope = rememberCoroutineScope()
    var notes by remember { mutableStateOf(listOf<NoteEntity>()) }
    var searchQuery by remember { mutableStateOf("") }
    var sortDescending by remember { mutableStateOf(true) }

    // Dialog state
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<NoteEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf<NoteEntity?>(null) }

    LaunchedEffect(Unit) {
        notes = noteDao.getAllNotes()
    }

    val filteredNotes = notes
        .filter { it.content.contains(searchQuery, ignoreCase = true) }
        .sortedByDescending { if (sortDescending) it.id else -it.id }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Button(
            onClick = { navController.navigate(Screen.Upgrade.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open Upgrade Screen (Demo)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search field with button
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search notes") },
            trailingIcon = {
                IconButton(onClick = { */
/* maybe clear search *//*
 searchQuery = "" }) {
                    Icon(Icons.Default.Search, contentDescription = "Clear search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Note")
            }

            Button(onClick = { sortDescending = !sortDescending }) {
                Text(if (sortDescending) "Sort: Newest" else "Sort: Oldest")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredNotes) { note ->
                NoteItem(
                    note = note,
                    onEdit = {
                        editingNote = it
                        showEditDialog = true
                    },
                    onDelete = {
                        showDeleteDialog = it
                    }
                )
            }
        }
    }

    // Add Note Dialog
    if (showAddDialog) {
        var text by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Note") },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Note content") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (text.isNotBlank()) {
                        scope.launch {
                            noteDao.insert(NoteEntity(content = text))
                            notes = noteDao.getAllNotes()
                        }
                        showAddDialog = false
                    }
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { showAddDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Edit Note Dialog
    if (showEditDialog && editingNote != null) {
        var text by remember { mutableStateOf(editingNote!!.content) }
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Note") },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Note content") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (text.isNotBlank()) {
                        val noteToEdit = editingNote ?: return@Button
                        scope.launch {
                            noteDao.update(noteToEdit.id, text)
                            notes = noteDao.getAllNotes()
                        }
                        showEditDialog = false
                        editingNote = null
                    }
                }) { Text("Update") }
            },
            dismissButton = {
                Button(onClick = { showEditDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                Button(onClick = {
                    val noteToDelete = showDeleteDialog ?: return@Button
                    scope.launch {
                        noteDao.delete(noteToDelete)
                        notes = noteDao.getAllNotes()
                    }
                    showDeleteDialog = null
                }) { Text("Delete") }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = null }) { Text("Cancel") }
            }
        )
    }
}
*/

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import work.erlend.securenotesdemo.common.data.local.NoteDao
import work.erlend.securenotesdemo.common.data.local.NoteEntity
import work.erlend.securenotesdemo.navigation.Screen

private const val MAX_NOTE_LENGTH = 300

@Composable
fun NotesScreen(noteDao: NoteDao, navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var notes by remember { mutableStateOf(listOf<NoteEntity>()) }
    var searchQuery by remember { mutableStateOf("") }
    var sortDescending by remember { mutableStateOf(true) }

    // Dialog state
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<NoteEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf<NoteEntity?>(null) }

    LaunchedEffect(Unit) {
        notes = noteDao.getAllNotes()
    }

    val filteredNotes = notes
        .filter { it.content.contains(searchQuery, ignoreCase = true) }
        .sortedByDescending { if (sortDescending) it.id else -it.id }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Button to navigate to UpgradeScreen
        Button(
            onClick = { navController.navigate(Screen.Upgrade.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open Upgrade Screen (Demo)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search field with clear button
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search notes") },
            trailingIcon = {
                IconButton(onClick = { searchQuery = "" }) {
                    Icon(Icons.Default.Search, contentDescription = "Clear search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add / Sort buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Note")
            }

            Button(onClick = { sortDescending = !sortDescending }) {
                Text(if (sortDescending) "Sort: Newest" else "Sort: Oldest")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Note list
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredNotes) { note ->
                NoteItem(
                    note = note.copy(
                        content = if (note.content.length > MAX_NOTE_LENGTH) note.content.take(
                            MAX_NOTE_LENGTH
                        ) + "…" else note.content
                    ),
                    onEdit = {
                        editingNote = it
                        showEditDialog = true
                    },
                    onDelete = {
                        showDeleteDialog = it
                    }
                )
            }
        }
    }

    // Add Note Dialog
    if (showAddDialog) {
        var text by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Note") },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Note content") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    Log.d("NotesScreen", "Trying to add note with content: ${text}")

                    if (text.isBlank()) return@Button
                    if (text.length > MAX_NOTE_LENGTH) {
                        Toast.makeText(context, "Note cannot exceed $MAX_NOTE_LENGTH characters", Toast.LENGTH_SHORT).show()
                        Log.d("NotesScreen", "Note cannot exceed $MAX_NOTE_LENGTH characters")
                        return@Button
                    }
                    scope.launch {
                        try {
                            noteDao.insert(NoteEntity(content = text))
                            notes = noteDao.getAllNotes()
                            Log.d("NotesScreen", "Successfully saved note: ${text}")
                        } catch (e: Exception) {
                            Log.d("NotesScreen", "Error saving note: ${e.message}")
                            Toast.makeText(context, "Error saving note: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    showAddDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { showAddDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Edit Note Dialog
    if (showEditDialog && editingNote != null) {
        var text by remember { mutableStateOf(editingNote!!.content) }
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Note") },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Note content") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (text.isBlank()) return@Button
                    if (text.length > MAX_NOTE_LENGTH) {
                        Toast.makeText(context, "Note cannot exceed $MAX_NOTE_LENGTH characters", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val noteToEdit = editingNote ?: return@Button
                    scope.launch {
                        try {
                            noteDao.update(noteToEdit.id, text)
                            notes = noteDao.getAllNotes()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error updating note: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    showEditDialog = false
                    editingNote = null
                }) { Text("Update") }
            },
            dismissButton = {
                Button(onClick = { showEditDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                Button(onClick = {
                    val noteToDelete = showDeleteDialog ?: return@Button
                    scope.launch {
                        try {
                            noteDao.delete(noteToDelete)
                            notes = noteDao.getAllNotes()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error deleting note: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    showDeleteDialog = null
                }) { Text("Delete") }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = null }) { Text("Cancel") }
            }
        )
    }
}
