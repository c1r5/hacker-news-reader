package dev.eric.hnreader.models

import dev.eric.hnreader.models.dtos.ApiResponseDTO
import io.ktor.client.HttpClient

interface HackerNewsService {
    val httpClient: HttpClient
    val baseURL: String
    val defaultPayload: SearchPayload get() = SearchPayload()
    suspend fun search(payload: SearchPayload): Result<ApiResponseDTO>
}

data class SearchPayload(
    var query: String? = null,
    var tags: List<SearchTags> = emptyList(),
    var page: Int? = null,
    var hitsPerPage: Int = 20,
    var customFilter: NumericFilter? = null,
    var byDate: Boolean = false,
)

data class NumericFilter(val min: Int, val max: Int? = null) {
    fun createdAt(): String =
        "created_at_i>=${min}" + if (max != null) ",created_at_i<=${max}" else ""

    fun score(): String = "points>=${min}" + if (max != null) ",points<=${max}" else ""
    fun comments(): String =
        "num_comments>=${min}" + if (max != null) ",num_comments<=${max}" else ""
}

enum class SearchTags(val tag: String) {
    STORY("story"),
    COMMENT("comment"),
    POLL("poll"),
    POLLOPT("pollopt"),
    SHOWHN("show_hn"),
    ASKHN("ask_hn"),
    FRONTPAGE("front_page")
}