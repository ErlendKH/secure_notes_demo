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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import work.erlend.securenotesdemo.common.ui.CarouselPage
import work.erlend.securenotesdemo.common.ui.InfoCarousel
import work.erlend.securenotesdemo.navigation.Screen

val testingPages = listOf(
    CarouselPage(
        title = "Unit Testing Android Components",
        content = buildAnnotatedString { append(
            "Unit tests verify individual Android components like ViewModels and utility classes.")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Unit tests") }
            append(" run fast and help catch logic errors early. Use ")
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) { append("JUnit") }
            append(" and Kotlin test frameworks.")
        }
    ),
    CarouselPage(
        title = "Instrumented Tests on Device/Emulator",
        content = buildAnnotatedString {
            append("Instrumented tests run on a real Android device or emulator. ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Instrumented tests") }
            append(" verify UI interactions, databases, and navigation. Use ")
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                append("Espresso") }
            append(" and AndroidX Test for UI testing.")
        }
    ),
    CarouselPage(
        title = "Best Practices for Android Testing",
        content = buildAnnotatedString {
            append("Best practices:\n\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("• Keep tests small and independent\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("• Use test doubles (mocks/stubs) for dependencies\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("• Automate tests with CI/CD pipelines\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("• Measure code coverage (e.g., with JaCoCo)") }
        }
    )
)

/**
 * Displays a carousel of pages explaining testing strategies for Android apps.
 *
 * Uses [InfoCarousel] to show the pages defined in [testingPages], with navigation
 * buttons to return to the Theory menu or proceed to Agile info.
 *
 * @param navController the [NavController] used for navigation
 */
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
            InfoCarousel(
                pages = testingPages,
                onReturn = { navController.navigate(Screen.Theory.route)},
                navigateNext = { navController.navigate(Screen.Agile.route) }
            )
        }
    }

}