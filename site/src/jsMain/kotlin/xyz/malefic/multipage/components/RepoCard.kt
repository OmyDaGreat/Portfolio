package xyz.malefic.multipage.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ObjectFit.Companion.Contain
import com.varabyte.kobweb.compose.css.TextOverflow.Companion.Ellipsis
import com.varabyte.kobweb.compose.foundation.layout.Arrangement.SpaceBetween
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment.CenterVertically
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Color.Rgb
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.DisplayStyle.Companion.Flex
import org.jetbrains.compose.web.css.LineStyle.Companion.Solid
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.AlignItems.Companion.Center as AlignCenter
import org.jetbrains.compose.web.css.Color as ComposeColor
import org.jetbrains.compose.web.css.JustifyContent.Companion.Center as JustifyCenter

val neonGreen = neonGreenWithAlpha(1f)

/**
 * Returns a neon green color with the specified alpha value.
 *
 * @param alpha The alpha value for the color.
 * @return The neon green color with the specified alpha value.
 */
fun neonGreenWithAlpha(alpha: Float): Rgb = Color.rgba(57, 255, 20, alpha)

val RepoCardStyle =
    CssStyle {
        base {
            Modifier
                .display(Flex)
                .borderRadius(16.px)
                .border(2.px, Solid, neonGreen)
                .backgroundColor(ComposeColor("#0D0D0D"))
                .color(neonGreen)
                .padding(16.px)
                .width(100.percent)
                .maxWidth(800.px)
                .boxShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 8.px, color = neonGreenWithAlpha(0.5f))
        }
    }

val RepoNameStyle =
    CssStyle {
        base {
            Modifier
                .fontSize(24.px)
                .fontWeight(700)
                .margin(bottom = 4.px)
                .textShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 4.px, color = neonGreenWithAlpha(0.7f))
        }
    }

val RepoDescriptionStyle =
    CssStyle {
        base {
            Modifier
                .fontSize(14.px)
                .opacity(0.8)
                .maxWidth(400.px)
                .textOverflow(Ellipsis)
        }
    }

val ImageContainerStyle =
    CssStyle {
        base {
            Modifier
                .display(Flex)
                .justifyContent(JustifyCenter)
                .alignItems(AlignCenter)
                .border(2.px, Solid, neonGreen)
                .borderRadius(8.px)
                .backgroundColor(Black)
                .width(200.px)
                .height(120.px)
                .padding(16.px)
                .boxShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 6.px, color = neonGreenWithAlpha(0.4f))
        }
    }

/**
 * A composable function that displays a repository card with the given information.
 *
 * @param repoName The name of the repository.
 * @param description A short description of the repository.
 * @param imageUrl An optional URL for the repository image. If null, a default "X" is displayed.
 */
@Composable
fun RepoCard(
    repoName: String,
    description: String,
    imageUrl: String? = null,
) {
    Row(
        RepoCardStyle.toModifier(),
        horizontalArrangement = SpaceBetween,
        verticalAlignment = CenterVertically,
    ) {
        // Left side: Repo information
        Column(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(right = 16.px)
                    .flexGrow(1),
        ) {
            // Repo name
            Box(RepoNameStyle.toModifier()) {
                Text(repoName)
            }

            // Repo description
            Box(RepoDescriptionStyle.toModifier()) {
                Text(description)
            }
        }

        // Right side: Image container with X
        Box(ImageContainerStyle.toModifier()) {
            if (imageUrl != null) {
                Img(
                    src = imageUrl,
                    attrs =
                        Modifier
                            .fillMaxSize()
                            .objectFit(Contain)
                            .toAttrs(),
                )
            } else {
                // Default "X" content
                SpanText(
                    "✕",
                    Modifier
                        .fontSize(60.px)
                        .fontWeight(300)
                        .color(neonGreen)
                        // Add text shadow for neon text effect
                        .textShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 4.px, color = neonGreenWithAlpha(0.7f)),
                )
            }
        }
    }
}

/**
 * An example usage of the RepoCard composable function.
 */
@Composable
fun RepoCardExample() {
    RepoCard(
        repoName = "REPO NAME",
        description = "short description lorem ipsum dolor sit amet",
    )
}
