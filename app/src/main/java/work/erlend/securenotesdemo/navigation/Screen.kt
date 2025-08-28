package work.erlend.securenotesdemo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents the top-level and nested navigation destinations in the Secure Notes Demo app.
 *
 * Each screen defines:
 * - [route]: the unique navigation route used by [NavController]
 * - [title]: the human-readable title for UI elements (e.g., bottom navigation labels)
 * - [icon]: the [ImageVector] to display in the bottom navigation bar
 *
 * Using a sealed class ensures type safety when navigating between screens.
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Notes : Screen("notes", "Notes", Icons.Filled.Home)
    object Upgrade : Screen("notes/upgrade", "Upgrade", Icons.Filled.Settings)
    object Theory : Screen("theory", "Theory", Icons.Filled.Info)
    object Agile : Screen("theory/agile", "Agile Info", Icons.Filled.Info)
    object Testing : Screen("theory/testing", "Testing Info", Icons.Filled.Info)
    object Security : Screen("theory/security", "Security Info", Icons.Filled.Info)
}