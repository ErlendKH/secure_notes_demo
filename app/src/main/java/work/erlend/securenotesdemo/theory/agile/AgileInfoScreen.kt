package work.erlend.securenotesdemo.theory.agile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

val agilePages = listOf(
    CarouselPage(
        title = "Iterative Android Development",
        content = buildAnnotatedString {
            append("Agile Android development is an ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("iterative") }
            append(" approach emphasizing ")
            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("continuous feedback") }
            append(" and collaboration between developers, testers, and product owners.")
        }
    ),
    CarouselPage(
        title = "Sprints & Feature Delivery",
        content = buildAnnotatedString {
            append("Teams work in ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("sprints") }
            append(", short cycles where Android features are planned, implemented, and reviewed.")
        }
    ),
    CarouselPage(
        title = "Core Agile Values",
        content = buildAnnotatedString {
            append("Agile values in Android projects:\n\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Team collaboration\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Functional Android builds\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Customer feedback\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Responding to change") }
        }
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgileInfoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agile Info") },
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
            InfoCarousel(pages = agilePages)
        }
    }
}