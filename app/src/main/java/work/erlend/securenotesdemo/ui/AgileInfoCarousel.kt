package work.erlend.securenotesdemo.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgileInfoCarousel() {
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

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope() // for clickable dots

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Text(
                text = pages[page],
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                // textAlign = TextAlign.Start by default (left-aligned)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Animated & clickable dot indicators
        Row(horizontalArrangement = Arrangement.Center) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                val size by animateDpAsState(targetValue = if (isSelected) 12.dp else 8.dp)

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(size)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                )
            }
        }
    }
}