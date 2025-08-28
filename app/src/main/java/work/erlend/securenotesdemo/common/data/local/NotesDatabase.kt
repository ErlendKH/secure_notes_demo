/*
 * Secure Notes Demo
 * Copyright (C) 2025 Erlend H.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package work.erlend.securenotesdemo.common.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase

/**
 * Encrypted Room database for storing notes securely.
 *
 * Uses SQLCipher to encrypt the database with a passphrase, ensuring that
 * sensitive note data is protected even if the APK or device storage is compromised.
 *
 * Provides access to [NoteDao] for performing CRUD operations on notes.
 */
@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        /**
         * Retrieves or creates the singleton [NotesDatabase] instance.
         *
         * @param context the application [Context] used to build the database
         * @param passphrase the encryption passphrase for SQLCipher
         * @return the singleton [NotesDatabase] instance
         */
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