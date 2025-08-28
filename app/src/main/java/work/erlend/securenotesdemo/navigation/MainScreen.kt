package work.erlend.securenotesdemo.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import work.erlend.securenotesdemo.common.data.local.NoteRepository
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import work.erlend.securenotesdemo.theory.agile.AgileInfoScreen
import work.erlend.securenotesdemo.notes.NotesScreen
import work.erlend.securenotesdemo.theory.TheoryScreen
import work.erlend.securenotesdemo.theory.security.SecurityInfoScreen
import work.erlend.securenotesdemo.theory.testing.TestingInfoScreen
import work.erlend.securenotesdemo.notes.upgrade.UpgradeScreen

/**
 * The main navigation Composable for the Secure Notes Demo app.
 *
 * This function sets up:
 * - A [Scaffold] with a bottom navigation bar containing the main tabs
 *   (Notes and Theory)
 * - A [NavHost] to manage navigation between screens
 * - Injection of [NotesDatabase] and [NoteRepository] into the relevant screens
 *
 * Each tab in the bottom navigation corresponds to a top-level screen:
 * - [Screen.Notes] → displays [NotesScreen]
 * - [Screen.Theory] → displays [TheoryScreen] and related info screens
 *
 * Additional screens like [UpgradeScreen], [AgileInfoScreen], [TestingInfoScreen],
 * and [SecurityInfoScreen] are included in the navigation graph.
 *
 * @param database the encrypted [NotesDatabase] instance used in screens that require it
 * @param noteRepository the [NoteRepository] providing access to note data
 */
@Composable
fun MainScreen(database: NotesDatabase, noteRepository: NoteRepository) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                listOf(Screen.Notes, Screen.Theory).forEach { screen ->
                    val selected = isParentTabSelected(screen.route, currentRoute)

                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigate(screen.route) {
                            launchSingleTop = true } },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        modifier = Modifier.background(if (selected)
                            Color.LightGray else Color.Transparent),
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Notes.route) {
                NotesScreen(noteRepository = noteRepository, navController = navController) }
            composable(Screen.Upgrade.route) {
                UpgradeScreen(
                    navController = navController,
                    database = database,
                )
            }

            composable(Screen.Theory.route) { TheoryScreen(navController) }
            composable(Screen.Agile.route) { AgileInfoScreen(navController) }
            composable(Screen.Testing.route) { TestingInfoScreen(navController) }
            composable(Screen.Security.route) { SecurityInfoScreen(navController) }
        }
    }
}

/**
 * Determines whether a parent tab is currently selected based on the navigation route.
 *
 * This helps highlight the active tab in the bottom navigation bar.
 *
 * @param parentRoute the route of the parent tab (e.g., "Notes" or "Theory")
 * @param currentRoute the current route from the NavController
 * @return `true` if the current route starts with the parent route, `false` otherwise
 */
fun isParentTabSelected(parentRoute: String, currentRoute: String?): Boolean {
    return currentRoute?.startsWith(parentRoute) == true
}