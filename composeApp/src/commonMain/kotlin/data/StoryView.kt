package data

import dev.eric.hnreader.util.elapsedHours

data class StoryView(
    val id: Long?,
    val author: String?,
    val title: String?,
    val postedAt: String?,
    val scoreCount: Int?,
    val commentsCount: Int?,
) {
    companion object {
        fun from(story: Story?): StoryView = StoryView(
            id = story?.id,
            title = story?.title,
            author = story?.by,
            scoreCount = story?.score,
            commentsCount = story?.descendants,
            postedAt = "${story?.time?.let { elapsedHours(it) }}h"
        )
    }
}