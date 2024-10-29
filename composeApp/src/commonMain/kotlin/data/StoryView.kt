package data

data class StoryView(
    val id: Long?,
    val author: String?,
    val title: String?,
    val postedAt: Long?,
    val scoreCount: Int?,
    val commentsCount: Int?,
) {
    companion object {
        fun from(story: Story?): StoryView? = story?.let {
            StoryView(
                id = story.id,
                title = story.title,
                author = story.by,
                scoreCount = story.score,
                commentsCount = story.descendants,
                postedAt = story.time
            )
        }
    }
}