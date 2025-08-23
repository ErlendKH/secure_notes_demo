package work.erlend.securenotesdemo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import work.erlend.securenotesdemo.navigation.MainScreen

@Composable
fun SecureNotesDemoApp() {
    MaterialTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AgileInfoCarousel()
//        }
        MainScreen()
    }
}