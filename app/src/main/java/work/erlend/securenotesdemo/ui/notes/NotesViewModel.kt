package work.erlend.securenotesdemo.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import work.erlend.securenotesdemo.data.CryptoManager
import work.erlend.securenotesdemo.data.local.NoteEntity
import work.erlend.securenotesdemo.data.local.NotesDatabase

class NotesViewModel(database: NotesDatabase) : ViewModel() {

    private val noteDao = database.noteDao()

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notes.value = noteDao.getAllNotes().map { it.copy(content = CryptoManager.decrypt(it.content)) }
        }
    }

    fun addNote(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            val encrypted = CryptoManager.encrypt(text)
            noteDao.insert(NoteEntity(content = encrypted))
            loadNotes()
        }
    }
}