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
                        onClick = { navController.navigate(screen.route) { launchSingleTop = true } },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        modifier = Modifier.background(if (selected) Color.LightGray else Color.Transparent),
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

fun isParentTabSelected(parentRoute: String, currentRoute: String?): Boolean {
    return currentRoute?.startsWith(parentRoute) == true
}