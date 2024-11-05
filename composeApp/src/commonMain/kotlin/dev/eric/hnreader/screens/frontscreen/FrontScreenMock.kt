package dev.eric.hnreader.screens.frontscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import dev.eric.hnreader.models.dtos.HitDTO

@Composable
fun FrontscreenMock(hits: List<HitDTO>) {
    LazyColumn {
        items(hits) { story ->
            NewsItem(story)
        }
    }
}