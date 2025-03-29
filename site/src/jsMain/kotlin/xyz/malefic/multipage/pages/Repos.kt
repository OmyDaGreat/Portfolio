package xyz.malefic.multipage.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import xyz.malefic.multipage.components.GitHubReposPage

@Page("/maleficcompose")
@Composable
fun MaleficComposeReposPage() {
    GitHubReposPage(username = "MaleficCompose")
}

@Page("/omydagreat")
@Composable
fun OmyDaGreatReposPage() {
    GitHubReposPage(username = "OmyDaGreat")
}
