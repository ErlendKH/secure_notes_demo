package work.erlend.securenotesdemo.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a note stored in the [NotesDatabase].
 *
 * @param id the unique identifier for the note (auto-generated)
 * @param content the textual content of the note
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String
)