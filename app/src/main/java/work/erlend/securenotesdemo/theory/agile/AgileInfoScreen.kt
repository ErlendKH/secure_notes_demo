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
import work.erlend.securenotesdemo.common.ui.InfoCarousel

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

//            AgileInfoCarousel()

            val pages = listOf(
                buildAnnotatedString {
                    append("Agile software development is an ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("iterative") }
                    append(" approach that emphasizes ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("continuous feedback") }
                    append(" and collaboration.")
                },
                buildAnnotatedString {
                    append("Teams work in ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("sprints") }
                    append(", short cycles where software is planned, built, and reviewed.")
                },
                buildAnnotatedString {
                    append("Agile values:\n")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Individuals and interactions\n") }
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Working software\n") }
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Customer collaboration\n") }
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Responding to change") }
                }
            )
            InfoCarousel(pages = pages)

        }
    }

//    AgileInfoCarousel()
}