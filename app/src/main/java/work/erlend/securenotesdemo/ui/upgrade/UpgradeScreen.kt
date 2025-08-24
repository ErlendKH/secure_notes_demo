package work.erlend.securenotesdemo.ui.upgrade

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import work.erlend.securenotesdemo.data.KeystoreHelper
import work.erlend.securenotesdemo.data.PassphraseManager
import work.erlend.securenotesdemo.data.local.NotesDatabase
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpgradeScreen(
    navController: NavController,
    database: NotesDatabase,
    onPassphraseUpdated: (newPass: String) -> Unit
) {
    val context = LocalContext.current
    val prefsPass = PassphraseManager(context).getPassphrase()

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
                val keystoreKey = KeystoreHelper.getOrCreateAesKey("SecureNotesKey")
                Log.d("UpgradeScreen", "Migrated passphrase: $prefsPass to Keystore key $keystoreKey")
                Toast.makeText(context, "Passphrase migration simulated", Toast.LENGTH_SHORT).show()
            }) {
                Text("Migrate Passphrase to Keystore")
            }

            // Rekey button
            Button(onClick = {
                val newPass = UUID.randomUUID().toString()
                database.openHelper.writableDatabase.execSQL("PRAGMA rekey = '$newPass'")
                PassphraseManager(context).updatePassphrase(newPass)
                onPassphraseUpdated(newPass)
                Toast.makeText(context, "Database rekeyed with new passphrase", Toast.LENGTH_SHORT).show()
            }) {
                Text("Rekey Database (Simulate Upgrade)")
            }
        }
    }
}