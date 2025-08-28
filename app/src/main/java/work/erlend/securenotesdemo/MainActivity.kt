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

/**
 * The main entry point for the Secure Notes Demo app.
 *
 * This activity is responsible for:
 * - Generating or retrieving a secure passphrase from the Android Keystore
 * - Initializing the encrypted [NotesDatabase] with the passphrase
 * - Creating a [NoteRepository] to manage note operations
 * - Setting up and launching the Compose UI with [SecureNotesDemoApp]
 */
class MainActivity : ComponentActivity() {

    /**
     * Initializes the application.
     *
     * Securely retrieves a passphrase via [KeystorePassphraseManager], sets up
     * the encrypted Room [NotesDatabase], and injects the [NoteRepository] into
     * the app's top-level composable.
     *
     * @param savedInstanceState the saved state of the activity, or `null`
     * if none is available
     */
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