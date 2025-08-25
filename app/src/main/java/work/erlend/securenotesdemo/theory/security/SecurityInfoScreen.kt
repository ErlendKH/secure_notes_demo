package work.erlend.securenotesdemo.theory.security

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
import work.erlend.securenotesdemo.common.ui.CarouselPage
import work.erlend.securenotesdemo.common.ui.InfoCarousel
import work.erlend.securenotesdemo.navigation.Screen

val securityPages = listOf(
    CarouselPage(
        title = "Secure Data Storage",
        content = buildAnnotatedString {
            append("Store sensitive Android app data securely. ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("SQLCipher") }
            append(" encrypts your app database and protects notes if the APK is decompiled.")
        }
    ),
    CarouselPage(
        title = "APK Decompilation Risks",
        content = buildAnnotatedString {
            append("APK files can be decompiled, exposing source code and resources. ")
            append("Obfuscate code using ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("ProGuard") }
            append(" or ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("R8") }
            append(", and avoid hardcoding secrets in the app.")
        }
    ),
    CarouselPage(
        title = "App Hardening",
        content = buildAnnotatedString {
            append("Additional security tips for Android apps:\n\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Minimize permissions\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Protect API keys and secrets\n\n") }
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("• Keep dependencies updated (note: SQLCipher is outdated in this demo)\n") }
        }
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityInfoScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security Info") },
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
                pages = securityPages,
                onReturn = { navController.navigate(Screen.Theory.route)},
                navigateNext = { navController.navigate(Screen.Testing.route) }
            )
        }
    }

}