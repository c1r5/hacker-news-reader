import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.network.createHttpClient
import dev.eric.hnreader.services.HackerNewsServiceImpl
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestHackerNewsService {
    @Test
    fun testSearch(): Unit = runBlocking {
        val httpClient = createHttpClient(OkHttp.create())
        val hackerNewsService = HackerNewsServiceImpl(httpClient)
        val searchPayload = hackerNewsService.defaultPayload.apply {
            tags += SearchTags.FRONTPAGE
            byDate = true
            page = 0
        }

        hackerNewsService.search(searchPayload).onFailure {
            println(it)
        }.onSuccess {
            it.hits.forEach(::println)
        }

    }
}