package work.erlend.securenotesdemo.common.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object for performing CRUD operations on [NoteEntity].
 *
 * Defines database queries for inserting, updating, deleting, and fetching notes.
 */
@Dao
interface NoteDao {

    /** Inserts a new note into the database. */
    @Insert
    suspend fun insert(note: NoteEntity)

    /** Returns all notes ordered by descending ID (latest first). */
    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes(): List<NoteEntity>

    /** Updates the content of a note with the given [id]. */
    @Query("UPDATE notes SET content = :content WHERE id = :id")
    suspend fun update(id: Long, content: String)

    /** Deletes the given note from the database. */
    @Delete
    suspend fun delete(note: NoteEntity)

}