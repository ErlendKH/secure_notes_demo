package work.erlend.securenotesdemo.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import work.erlend.securenotesdemo.agile.AgileInfoScreen
import work.erlend.securenotesdemo.notes.NotesScreen
import work.erlend.securenotesdemo.upgrade.UpgradeScreen

@Composable
fun MainScreen(database: NotesDatabase) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                listOf(Screen.Notes, Screen.Agile).forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = { navController.navigate(screen.route) { launchSingleTop = true } },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) }
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
                NotesScreen(noteDao = database.noteDao(), navController = navController) }
            composable(Screen.Upgrade.route) {
                UpgradeScreen(
                    navController = navController,
                    database = database,
                )
            }
            composable(Screen.Agile.route) { AgileInfoScreen() }
        }
    }
}