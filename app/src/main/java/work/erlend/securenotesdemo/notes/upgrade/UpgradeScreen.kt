package work.erlend.securenotesdemo.notes.upgrade

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import work.erlend.securenotesdemo.common.data.security.KeystorePassphraseManager
import work.erlend.securenotesdemo.common.data.local.NotesDatabase

/**
 * Demo screen for generating and displaying the current database passphrase,
 * and securely rekeying the database with a new passphrase.
 *
 * Provides buttons to show the passphrase and to trigger a database rekey via
 * [KeystorePassphraseManager]. Rekeying occurs on a background IO coroutine.
 *
 * @param navController used to navigate back to the previous screen
 * @param database the [NotesDatabase] instance to rekey
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpgradeScreen(
    navController: NavController,
    database: NotesDatabase
) {
    val context = LocalContext.current
    val ioScope = CoroutineScope(Dispatchers.IO)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upgrade / Passphrase Demo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Show or generate current passphrase
            Button(onClick = {
                val pass = KeystorePassphraseManager.getOrCreatePassphrase(context)
                Toast.makeText(context, "Current passphrase: $pass", Toast.LENGTH_LONG).show()
            }) {
                Text("Show / Generate Passphrase")
            }

            // Rekey database safely
            Button(onClick = {
                ioScope.launch {
                    try {
                        KeystorePassphraseManager.rekeyDatabase(context, database)
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "Database rekeyed!",
                                Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("UpgradeScreen", "Failed to rekey database: ${e.message}")
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                context,
                                "Failed to rekey database: ${e.stackTraceToString()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }) {
                Text("Rekey Database")
            }
        }
    }
}