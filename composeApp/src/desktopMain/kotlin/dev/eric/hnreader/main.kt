package dev.eric.hnreader

import KoinInit
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.StoryView
import dev.eric.hnreader.screens.StoryListMock

fun main() = application {
    KoinInit().init()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Hacker News Reader",
    ) {
        App()
    }
}

@Preview
@Composable
fun PreviewStoryList() {
    MaterialTheme {
        StoryListMock(
            List(10) {
                StoryView(
                    id = it.toLong(),
                    author = "Author $it",
                    title = "Title $it",
                    postedAt = 1730144578294,
                    commentsCount = 10,
                    scoreCount = 10 + it
                )
            }
        )
    }
}