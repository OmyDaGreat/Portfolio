@file:Suppress("kotlin:S117", "PropertyName")

package xyz.malefic.multipage.api

import kotlinx.browser.window
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.js.Promise

@Serializable
data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    val html_url: String,
    val owner: Owner,
    val stargazers_count: Int,
    val forks_count: Int,
    val language: String?,
)

@Serializable
data class Owner(
    val login: String,
    val avatar_url: String,
)

object GitHubApi {
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchUserRepos(username: String): Promise<List<Repository>> =
        window
            .fetch("https://api.github.com/users/$username/repos?per_page=100")
            .then { response ->
                if (!response.ok) {
                    throw Error("Failed to fetch repositories for $username: ${response.statusText}")
                }
                response.text()
            }.then { text ->
                json.decodeFromString<List<Repository>>(text)
            }
}
