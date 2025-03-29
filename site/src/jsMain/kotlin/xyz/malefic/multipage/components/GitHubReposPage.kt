package xyz.malefic.multipage.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.TextAlign.Companion.Center
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H1
import xyz.malefic.multipage.api.GitHubApi
import xyz.malefic.multipage.api.Repository

/**
 * A reusable component for displaying GitHub repositories for a specific user.
 *
 * @param username The GitHub username whose repositories to fetch
 * @param pageTitle The title to display at the top of the page
 */
@Composable
fun GitHubReposPage(
    username: String,
    pageTitle: String = "$username's Repositories",
) {
    val scope = rememberCoroutineScope()
    var repos by remember { mutableStateOf<List<Repository>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val fetchedRepos = GitHubApi.fetchUserRepos(username)
                repos = fetchedRepos.await()
                isLoading = false
            } catch (e: Exception) {
                error = e.message
                isLoading = false
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.px)
                .backgroundColor(Color("#121212"))
                .minHeight(100.vh),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        H1(
            attrs =
                Modifier
                    .color(neonGreen)
                    .textShadow(offsetX = 0.px, offsetY = 0.px, blurRadius = 4.px, color = neonGreenWithAlpha(0.7f))
                    .textAlign(Center)
                    .margin(bottom = 32.px)
                    .toAttrs(),
        ) {
            SpanText(pageTitle)
        }

        RepoColumnFallback(isLoading, error, repos)
    }
}
