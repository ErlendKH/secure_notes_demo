package work.erlend.securenotesdemo.common.ui

import androidx.compose.ui.text.AnnotatedString

/**
 * Represents a single page in an [InfoCarousel].
 *
 * @param title the page title displayed at the top
 * @param content the scrollable page content as an [AnnotatedString]
 */
data class CarouselPage(
    val title: String,
    val content: AnnotatedString
)