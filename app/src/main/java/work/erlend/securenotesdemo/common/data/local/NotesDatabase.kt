package work.erlend.securenotesdemo.common.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context, passphrase: String): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_db"
                )
                    .openHelperFactory(SQLiteDatabase.getBytes(passphrase.toCharArray()).let {
                        net.sqlcipher.database.SupportFactory(it)
                    })
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }
}