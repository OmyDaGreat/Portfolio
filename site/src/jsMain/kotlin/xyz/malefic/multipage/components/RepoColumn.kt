package xyz.malefic.multipage.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.Cursor.Companion.Pointer
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronLeft
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronRight
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Position.Companion.Absolute
import org.jetbrains.compose.web.css.Position.Companion.Relative
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import xyz.malefic.multipage.api.Repository

@Composable
internal fun RepoColumn(repos: List<Repository>) {
    var currentIndex by remember { mutableStateOf(0) }
    val cardsPerView = rememberCardsPerView()

    val canScrollLeft = currentIndex > 0
    val canScrollRight = currentIndex < repos.size - cardsPerView
    val totalPages = (repos.size - cardsPerView + 1).coerceAtLeast(0)

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .maxWidth(1200.px)
                .position(Relative),
    ) {
        // Navigation controls
        NavigationButtons(
            canScrollLeft = canScrollLeft,
            canScrollRight = canScrollRight,
            onPrevious = { currentIndex = (currentIndex - 1).coerceAtLeast(0) },
            onNext = { currentIndex = (currentIndex + 1).coerceAtMost(repos.size - cardsPerView) },
        )

        // Repository cards
        RepoCardsContainer(
            repos = repos,
            currentIndex = currentIndex,
            cardsPerView = cardsPerView,
        )

        // Pagination indicators
        if (repos.size > cardsPerView) {
            PaginationIndicators(
                totalPages = totalPages,
                currentIndex = currentIndex,
                onPageSelected = { currentIndex = it },
            )
        }
    }
}

@Composable
private fun rememberCardsPerView(): Int {
    var cardsPerView by remember { mutableStateOf(1) }

    val resizeListener =
        remember {
            { _: dynamic ->
                cardsPerView = calculateCardsPerView(window.innerWidth.toDouble())
            }
        }

    LaunchedEffect(Unit) {
        cardsPerView = calculateCardsPerView(window.innerWidth.toDouble())
        window.addEventListener("resize", resizeListener)
    }

    DisposableEffect(Unit) {
        onDispose {
            window.removeEventListener("resize", resizeListener)
        }
    }

    return cardsPerView
}

private fun calculateCardsPerView(width: Double): Int =
    when {
        width > 1200 -> 3
        width > 768 -> 2
        else -> 1
    }

@Composable
private fun NavigationButtons(
    canScrollLeft: Boolean,
    canScrollRight: Boolean,
    onPrevious: (SyntheticMouseEvent) -> Unit,
    onNext: (SyntheticMouseEvent) -> Unit,
) {
    if (canScrollLeft) {
        NavigationButton(isLeft = true, onClick = onPrevious)
    }

    if (canScrollRight) {
        NavigationButton(isLeft = false, onClick = onNext)
    }
}

@Composable
private fun NavigationButton(
    isLeft: Boolean,
    onClick: (SyntheticMouseEvent) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .position(Absolute)
                .zIndex(10)
                .size(40.px)
                .cursor(Pointer)
                .styleModifier {
                    property("top", "50%")
                    property(if (isLeft) "left" else "right", "10px")
                    property("transform", "translateY(-50%)")
                }.onClick(onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (isLeft) {
            FaChevronLeft(modifier = Modifier.color(neonGreen))
        } else {
            FaChevronRight(modifier = Modifier.color(neonGreen))
        }
    }
}

@Composable
private fun RepoCardsContainer(
    repos: List<Repository>,
    currentIndex: Int,
    cardsPerView: Int,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .overflow(Overflow.Hidden),
    ) {
        Row(
            modifier =
                Modifier
                    .transition(Transition.of(property = "transform", duration = 500.ms))
                    .translateX((-currentIndex * (100 / cardsPerView)).percent),
            horizontalArrangement = Arrangement.spacedBy(16.px),
        ) {
            repos.forEachIndexed { index, repo ->
                RepoCardItem(
                    repo = repo,
                    isVisible = index >= currentIndex && index < currentIndex + cardsPerView,
                    cardsPerView = cardsPerView,
                )
            }
        }
    }
}

@Composable
private fun RepoCardItem(
    repo: Repository,
    isVisible: Boolean,
    cardsPerView: Int,
) {
    Box(
        modifier =
            Modifier
                .styleModifier {
                    property("flex", "0 0 ${100 / cardsPerView}%")
                }.padding(8.px)
                .transition(Transition.of(property = "opacity", duration = 300.ms))
                .opacity(if (isVisible) 1.0 else 0.3),
    ) {
        RepoCard(
            repoName = repo.name,
            description = repo.description ?: "No description available",
            imageUrl =
                repo.language?.let {
                    "https://raw.githubusercontent.com/github/explore/main/topics/$it/$it.png"
                },
        )
    }
}

@Composable
private fun PaginationIndicators(
    totalPages: Int,
    currentIndex: Int,
    onPageSelected: (Int) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .margin(top = 24.px)
                .position(Absolute)
                .styleModifier {
                    property("bottom", "-40px")
                    property("left", "0")
                },
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(totalPages) { i ->
            PaginationDot(
                isActive = i == currentIndex,
                onClick = { onPageSelected(i) },
            )
        }
    }
}

@Composable
private fun PaginationDot(
    isActive: Boolean,
    onClick: (SyntheticMouseEvent) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(10.px)
                .margin(right = 8.px)
                .cursor(Pointer)
                .styleModifier {
                    property("border-radius", "50%")
                    property(
                        "background-color",
                        if (isActive) neonGreen.toString() else "rgba(57, 255, 20, 0.3)",
                    )
                }.onClick(onClick),
    )
}
