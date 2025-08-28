package work.erlend.securenotesdemo.common.data.local

/**
 * Repository for managing note data through [NoteDao].
 *
 * Provides a clean API for adding, retrieving, updating, and deleting notes.
 * Handles any data layer logic if needed in the future.
 *
 * @param noteDao the [NoteDao] used to access the database
 */
class NoteRepository(private val noteDao: NoteDao) {

    /** Adds a new note to the database. */
    suspend fun addNote(note: NoteEntity) {
        noteDao.insert(note)
    }

    /** Returns all notes from the database, latest first. */
    suspend fun getAllNotes(): List<NoteEntity> {
        return noteDao.getAllNotes()
    }

    /** Updates the content of the note with the given [id]. */
    suspend fun updateNote(id: Long, content: String) {
        noteDao.update(id, content)
    }

    /** Deletes the given note from the database. */
    suspend fun deleteNote(note: NoteEntity) {
        noteDao.delete(note)
    }

}