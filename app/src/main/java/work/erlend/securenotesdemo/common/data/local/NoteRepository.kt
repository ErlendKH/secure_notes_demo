package work.erlend.securenotesdemo.common.data.local

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun addNote(note: NoteEntity) {
        noteDao.insert(note)
    }

    suspend fun getAllNotes(): List<NoteEntity> {
        return noteDao.getAllNotes()
    }

    suspend fun updateNote(id: Long, content: String) {
        noteDao.update(id, content)
    }

    suspend fun deleteNote(note: NoteEntity) {
        noteDao.delete(note)
    }
}