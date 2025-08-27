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

package work.erlend.securenotesdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import work.erlend.securenotesdemo.common.data.local.NoteRepository
import work.erlend.securenotesdemo.common.data.security.KeystorePassphraseManager
import work.erlend.securenotesdemo.common.data.local.NotesDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch or create passphrase securely
        val passphrase = KeystorePassphraseManager.getOrCreatePassphrase(this)

        // Initialize the database with the secure passphrase
        val db = NotesDatabase.getDatabase(this, passphrase)
        val noteRepository = NoteRepository(db.noteDao())

        setContent {
            SecureNotesDemoApp(db, noteRepository)
        }

    }
}