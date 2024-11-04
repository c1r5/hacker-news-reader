package dev.eric.hnreader.services

import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchPayload
import dev.eric.hnreader.models.dtos.ApiResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

const val PROD_URL = "https://hn.algolia.com"

class HackerNewsServiceImpl(
    override val httpClient: HttpClient,
    override val baseURL: String = "$PROD_URL/api/v1/"
) : HackerNewsService {
    override suspend fun search(payload: SearchPayload) = runCatching {
        val stringURL = baseURL + if (payload.byDate) "search_by_date" else "search"
        val response = httpClient.get(stringURL) {
            url {
                payload.query?.let { parameters.append("query", it) }
                payload.page?.let { parameters.append("page", it.toString()) }
                parameters.append("tags", payload.tags.joinToString(",") { it.tag })
                parameters.append("hitsPerPage", payload.hitsPerPage.toString())
            }
        }

        val content = response.bodyAsText()

        ApiResponseDTO.fromJson(content)
    }
}