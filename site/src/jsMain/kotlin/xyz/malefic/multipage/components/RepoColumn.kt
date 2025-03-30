package xyz.malefic.multipage.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronDown
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronUp
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import xyz.malefic.multipage.api.Repository

@Composable
internal fun RepoColumn(repos: List<Repository>) {
    // If repos is empty, don't render anything
    if (repos.isEmpty()) return

    // Create a circular version of repos for infinite scrolling
    // We need at least 3 items for proper display
    val circularRepos = if (repos.size >= 3) repos else List(3) { repos[it % repos.size] }

    // State for current center card index
    var centerIndex by remember { mutableStateOf(0) }

    // Auto-scrolling effect
    var autoScrollEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(autoScrollEnabled) {
        if (autoScrollEnabled) {
            while (true) {
                delay(5000) // Scroll every 5 seconds
                centerIndex = (centerIndex + 1) % circularRepos.size
            }
        }
    }

    // Define card heights
    val smallCardHeight = 180
    val largeCardHeight = 250
    val totalCarouselHeight = smallCardHeight * 2 + largeCardHeight + 40 // 40px for spacing

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .maxWidth(800.px)
                .height(totalCarouselHeight.px)
                .position(Position.Relative)
                .padding(top = 40.px, bottom = 40.px),
    ) {
        // Overlay navigation buttons
        NavigationButton(
            isUp = true,
            onClick = {
                autoScrollEnabled = false
                centerIndex = (centerIndex - 1 + circularRepos.size) % circularRepos.size
            },
        )

        NavigationButton(
            isUp = false,
            onClick = {
                autoScrollEnabled = false
                centerIndex = (centerIndex + 1) % circularRepos.size
            },
        )

        // Main carousel container
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(totalCarouselHeight.px)
                    .overflow(Overflow.Hidden),
        ) {
            // We need to display 3 cards: previous, current, and next
            for (offset in -1..1) {
                val index = (centerIndex + offset + circularRepos.size) % circularRepos.size
                val repo = circularRepos[index]

                // Position of card: -1 = top, 0 = center, 1 = bottom
                val isCentered = offset == 0
                val yPosition =
                    when (offset) {
                        -1 -> 0
                        0 -> smallCardHeight + 20 // Top card height + spacing
                        else -> smallCardHeight + largeCardHeight + 40 // Top + center heights + spacing
                    }

                RepositoryCard(
                    repo = repo,
                    isCentered = isCentered,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(if (isCentered) largeCardHeight.px else smallCardHeight.px)
                            .position(Position.Absolute)
                            .translateY(yPosition.px)
                            .transition(Transition.of("all", 500.ms))
                            .zIndex(if (isCentered) 2 else 1)
                            .scale(if (isCentered) 1.05 else 0.95)
                            .styleModifier {
                                property("transform-origin", "center")
                            },
                )
            }
        }
    }
}

@Composable
private fun NavigationButton(
    isUp: Boolean,
    onClick: (SyntheticMouseEvent) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(50.px)
                .position(Position.Absolute)
                .zIndex(10)
                .styleModifier {
                    property(if (isUp) "top" else "bottom", "0")
                    property("left", "50%")
                    property("transform", "translateX(-50%)")
                }.backgroundColor(neonGreenWithAlpha(0.1f))
                .borderRadius(25.px)
                .cursor(Cursor.Pointer)
                .transition(Transition.of("background-color", 300.ms))
                .onClick(onClick)
                .styleModifier {
                    property("&:hover", "{ background-color: ${neonGreenWithAlpha(0.2f)}; }")
                },
        contentAlignment = Alignment.Center,
    ) {
        if (isUp) {
            FaChevronUp(modifier = Modifier.color(neonGreen).size(20.px))
        } else {
            FaChevronDown(modifier = Modifier.color(neonGreen).size(20.px))
        }
    }
}

@Composable
private fun RepositoryCard(
    repo: Repository,
    isCentered: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .padding(leftRight = 16.px, topBottom = 8.px)
                .styleModifier {
                    property(
                        "background",
                        if (isCentered) {
                            "linear-gradient(145deg, #1e1e1e, #2a2a2a)"
                        } else {
                            "linear-gradient(145deg, #181818, #222222)"
                        },
                    )
                    property(
                        "box-shadow",
                        if (isCentered) {
                            "0 8px 16px rgba(57, 255, 20, 0.15)"
                        } else {
                            "0 4px 8px rgba(0, 0, 0, 0.2)"
                        },
                    )
                    property("border-radius", "12px")
                    property(
                        "border",
                        if (isCentered) {
                            "1px solid ${neonGreenWithAlpha(0.3f)}"
                        } else {
                            "1px solid rgba(255, 255, 255, 0.1)"
                        },
                    )
                },
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
