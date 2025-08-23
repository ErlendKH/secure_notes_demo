package work.erlend.securenotesdemo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import work.erlend.securenotesdemo.ui.AgileInfoCarousel

@Composable
fun SecureNotesDemoApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AgileInfoCarousel()
        }
    }
}