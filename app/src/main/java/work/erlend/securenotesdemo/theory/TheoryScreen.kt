package work.erlend.securenotesdemo.theory

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Displays the top-level theory menu for the app.
 *
 * Provides buttons to navigate to the individual theory topics.
 *
 * @param navController the [NavController] used to navigate to the selected theory screen
 */
@Composable
fun TheoryScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("theory/agile") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Agile")
        }

        Button(
            onClick = { navController.navigate("theory/security") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Security")
        }

        Button(
            onClick = { navController.navigate("theory/testing") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Testing")
        }

    }
}