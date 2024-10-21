import data.HackerNewsClient
import data.Story
import dev.eric.hnreader.network.createHttpClient
import dev.eric.hnreader.util.elapsedHours
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.runBlocking
import org.junit.Test

class HackerNewsTest {
    private val topStories = listOf(41879854, 41877513, 41879845, 41880295, 41876281)
    private val story = Story(
        by = "pehringer",
        descendants = 76,
        id = 41879854,
        kids = listOf(
            41882386, 41880731, 41880998, 41880371, 41883941, 41880902,
            41884353, 41880231, 41881696, 41883597, 41880773, 41879970,
            41880245, 41880346, 41880292, 41880278, 41880175
        ),
        score = 238,
        text = "A quick dive into the Plan9 assembly I picked up while developing my SIMD package for Go, and how it led to a 450% performance boost in calculations.",
        time = 1729262187,
        title = "Show HN: Go Plan9 Memo",
        type = "story",
        url = "https://pehringer.info/go_plan9_memo.html"
    )

    @Test
    fun shouldReturnTopStories() = runBlocking {
        val hackerNewsClient = HackerNewsClient(createHttpClient(OkHttp.create()))
        val topStories = hackerNewsClient.getTopStories()
        println(topStories.getOrNull())
        assert(topStories.isSuccess)
    }

    @Test
    fun shouldReturnStory() = runBlocking {
        val hackerNewsClient = HackerNewsClient(createHttpClient(OkHttp.create()))
        val story = hackerNewsClient.getStory(topStories.random().toLong())
        story.onFailure { println(it.message) }
        println(story.getOrNull())
        assert(story.isSuccess)
    }

    @Test
    fun shouldReturnStoryPostDatetime() {
        val elapsedHours = elapsedHours(story.time)

        println(elapsedHours)
        assert(elapsedHours in 12..18)
    }


}