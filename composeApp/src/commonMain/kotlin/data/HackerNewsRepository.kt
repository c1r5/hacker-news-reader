package data

class HackerNewsRepository(
    private val client: HackerNewsClient
) {

    suspend fun getTopStories(): Stories = client.getTopStories().getOrNull() ?: emptyList()

    suspend fun getStory(id: Long): Story? = client.getStory(id).getOrNull()

    fun getTopStoriesMock(): List<Story> = listOf(
        Story(
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
    )
}
