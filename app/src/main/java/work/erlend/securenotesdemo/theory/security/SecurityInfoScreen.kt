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
import work.erlend.securenotesdemo.common.ui.InfoCarousel
import work.erlend.securenotesdemo.theory.agile.AgileInfoCarousel

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

            val pages = listOf(
                buildAnnotatedString {
                    append("Use encrypted storage for sensitive data. ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("SQLCipher") }
                    append(" can encrypt databases, and ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("EncryptedSharedPreferences") }
                    append(" works for key-value storage. Never store passwords in plain text.")
                },
                buildAnnotatedString {
                    append("Always use ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("HTTPS") }
                    append(" with TLS for network communication. ")
                    append("Validate SSL certificates properly, and consider ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) { append("certificate pinning") }
                    append(" for extra security.")
                },
                buildAnnotatedString {
                    append("App Hardening & Permissions:\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Minimize permissions\n") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Obfuscate code using ProGuard or R8\n") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Protect API keys and secrets\n") }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("• Keep libraries updated to avoid vulnerabilities") }
                }
            )
            InfoCarousel(pages = pages)

//            Text("Android Security")
//            Spacer(modifier = Modifier.height(16.dp))
//            Text("This will later contain a carousel with security info.")
        }
    }

}