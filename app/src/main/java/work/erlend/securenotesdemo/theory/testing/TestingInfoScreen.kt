package work.erlend.securenotesdemo.theory.testing

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import work.erlend.securenotesdemo.common.ui.InfoCarousel
import work.erlend.securenotesdemo.theory.agile.AgileInfoCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestingInfoScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Testing Info") },
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

            val pages = listOf(
                buildAnnotatedString {
                    append("Unit tests verify individual components of your app in isolation. ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Unit tests") }
                    append(" run fast and help catch logic errors early. ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) { append("JUnit") }
                    append(" and Kotlin test frameworks are commonly used.")
                },
                buildAnnotatedString {
                    append("Instrumented tests run on a real device or emulator. ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Instrumented tests") }
                    append(" test interactions with Android components like Activities or Databases. ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) { append("Espresso") }
                    append(" and AndroidX Test are used for UI testing.")
                },
                buildAnnotatedString {
                    append("Best Practices:\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Keep tests small and independent\n") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Use test doubles (mocks/stubs)\n") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Run tests automatically with CI/CD\n") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Measure code coverage to track completeness") }
                }
            )
            InfoCarousel(pages = pages)

//            Text("Testing in Android")
//            Spacer(modifier = Modifier.height(16.dp))
//            Text("This will later contain a carousel with testing info.")

        }
    }

}