package xyz.malefic.multipage.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor.Companion.Pointer
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement.Center
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment.CenterHorizontally
import com.varabyte.kobweb.compose.ui.Alignment.CenterVertically
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle.Companion.Solid
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.H1
import xyz.malefic.multipage.components.neonGreen
import xyz.malefic.multipage.components.neonGreenWithAlpha

val NavLinkStyle =
    CssStyle {
        base {
            Modifier
                .color(neonGreen)
                .fontSize(18.px)
                .padding(12.px, 24.px)
                .border(2.px, Solid, neonGreen)
                .borderRadius(8.px)
                .backgroundColor(Black)
                .cursor(Pointer)
                .transition(Transition.all(0.3.s))
        }
        hover {
            Modifier
                .backgroundColor(neonGreenWithAlpha(0.2f))
                .boxShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 8.px, color = neonGreenWithAlpha(0.5f))
        }
    }

@Page
@Composable
fun HomePage() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .backgroundColor(Color("#121212"))
                .padding(24.px),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
    ) {
        H1(
            attrs =
                Modifier
                    .color(neonGreen)
                    .textShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 4.px, color = neonGreenWithAlpha(0.7f))
                    .margin(bottom = 48.px)
                    .toAttrs(),
        ) {
            SpanText("GitHub Repository Explorer")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Center,
            verticalAlignment = CenterVertically,
        ) {
            Link(
                path = "/omydagreat",
                text = "OmyDaGreat Repos",
                modifier = NavLinkStyle.toModifier().margin(right = 16.px),
            )

            Link(
                path = "/maleficcompose",
                text = "MaleficCompose Repos",
                modifier = NavLinkStyle.toModifier(),
            )
        }
    }
}
