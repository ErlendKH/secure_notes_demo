package work.erlend.securenotesdemo.common.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun InfoCarousel(
    pages: List<CarouselPage>,
    modifier: Modifier = Modifier,
    onReturn: () -> Unit,          // always required
    navigateNext: (() -> Unit)? = null, // optional
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top, // top-align everything
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Fixed title at the top
                Text(
                    text = pages[page].title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp)
                )

                // Scrollable content for the text
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f) // fill remaining space
                        .verticalScroll(rememberScrollState()) // allow scrolling if content is long
                ) {
                    Text(
                        text = pages[page].content,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons row
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp)
                ) {

                    // Optional Next button (only on last page if provided)
                    if (navigateNext != null && page == pages.lastIndex) {

                        androidx.compose.material3.Button(onClick = onReturn) {
                            Text("Return")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Warning: It may be appropriate to remove, if many theory pages are added.
                        androidx.compose.material3.Button(onClick = navigateNext) {
                            Text("Next")
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dot indicators (unchanged)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
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