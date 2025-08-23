package work.erlend.securenotesdemo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Notes : Screen("notes", "Notes", Icons.Filled.Home)
    object Agile : Screen("agile", "Agile Info", Icons.Filled.Info)
}