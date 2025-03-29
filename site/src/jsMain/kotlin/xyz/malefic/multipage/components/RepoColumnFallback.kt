package xyz.malefic.multipage.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import xyz.malefic.multipage.api.Repository

@Composable
internal fun RepoColumnFallback(
    isLoading: Boolean,
    error: String?,
    repos: List<Repository>,
) {
    when {
        isLoading -> {
            P(
                attrs =
                    Modifier
                        .color(neonGreen)
                        .fontSize(18.px)
                        .toAttrs(),
            ) {
                SpanText("Loading repositories...")
            }
        }

        error != null -> {
            P(
                attrs =
                    Modifier
                        .color(Color("#FF3939"))
                        .fontSize(18.px)
                        .toAttrs(),
            ) {
                SpanText("Error loading repositories: $error")
            }
        }

        repos.isEmpty() -> {
            P(
                attrs =
                    Modifier
                        .color(neonGreen)
                        .fontSize(18.px)
                        .toAttrs(),
            ) {
                SpanText("No repositories found")
            }
        }

        else -> {
            RepoColumn(repos)
        }
    }
}
