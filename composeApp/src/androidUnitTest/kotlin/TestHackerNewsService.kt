import dev.eric.hnreader.models.SearchPayload
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.ApiResponseDTO
import dev.eric.hnreader.network.createHttpClient
import dev.eric.hnreader.services.HackerNewsService
import dev.eric.hnreader.services.search
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestHackerNewsService {
    @Test
    fun testSearch(): Unit = runBlocking {
        val httpClient = createHttpClient(OkHttp.create())
        val hackerNewsService = HackerNewsService(httpClient)
        val searchPayload = SearchPayload(
            hitsPerPage = 10,
            tags = listOf(
                SearchTags.FRONTPAGE
            )
        )

        hackerNewsService.search<ApiResponseDTO>(searchPayload).onFailure {
            println(it)
        }.onSuccess {
            it.hits.forEach(::println)
        }

    }
}