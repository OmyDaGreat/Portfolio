package xyz.malefic.multipage.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import org.jetbrains.compose.web.css.px
import xyz.malefic.multipage.api.Repository

@Composable
internal fun RepoColumn(repos: List<Repository>) {
    Column(
        modifier = Modifier.fillMaxWidth().maxWidth(800.px),
        verticalArrangement = Arrangement.spacedBy(16.px),
    ) {
        repos.forEach { repo ->
            RepoCard(
                repoName = repo.name,
                description = repo.description ?: "No description available",
                imageUrl = repo.language?.let { "https://raw.githubusercontent.com/github/explore/main/topics/$it/$it.png" },
            )
        }
    }
}
