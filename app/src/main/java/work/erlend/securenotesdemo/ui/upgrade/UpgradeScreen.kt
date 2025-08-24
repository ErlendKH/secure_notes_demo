package work.erlend.securenotesdemo.ui.upgrade

/*
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
import work.erlend.securenotesdemo.data.NotesDatabaseHelper
import work.erlend.securenotesdemo.data.local.NotesDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpgradeScreen(
    navController: NavController,
    database: NotesDatabase,
) {
    val context = LocalContext.current

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
            // Migration button
            Button(onClick = {

                Toast.makeText(
                    context,
                    "Passphrase migration simulated (Keystore key created)",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text("Migrate Passphrase to Keystore")
            }

            // Rekey button
            Button(onClick = {
                NotesDatabaseHelper.rekeyDatabase(context, database)
            }) {
                Text("Rekey Database (Simulate Upgrade)")
            }
        }
    }
}
*/

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
import work.erlend.securenotesdemo.data.KeystorePassphraseManager
import work.erlend.securenotesdemo.data.local.NotesDatabase

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
                            Toast.makeText(context, "Database rekeyed!", Toast.LENGTH_SHORT).show()
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