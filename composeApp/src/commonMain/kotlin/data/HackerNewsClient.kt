package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

const val API_URL = "https://hacker-news.firebaseio.com"

typealias Stories = List<Long>

@Serializable
data class Story(
    val id: Long? = null,
    val deleted: Boolean = false,
    val type: String? = null,
    val by: String? = null,
    val time: Long? = null,
    val text: String? = null,
    val dead: Boolean = false,
    val parent: Long? = null,
    val poll: Long? = null,
    val kids: Stories? = null,
    val url: String? = null,
    val score: Int? = null,
    val title: String? = null,
    val parts: Stories? = null,
    val descendants: Int? = null,
)

class HackerNewsClient(private val httpClient: HttpClient) {
    private val topStoriesUrl = "$API_URL/v0/topstories.json"

    suspend fun getTopStories() = runCatching {
        val response = httpClient.get(topStoriesUrl)

        when (response.status.value) {
            in 200..299 -> response.body<Stories>()
            else -> throw Exception("Error getting top stories")
        }
    }

    suspend fun getStory(id: Long) = runCatching {
        val response = httpClient.get("$API_URL/v0/item/$id.json")
        when (response.status.value) {
            in 200..299 -> response.body<Story>()
            else -> throw Exception("Error getting story")
        }
    }
}