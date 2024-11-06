package dev.eric.hnreader.screens.trends

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.util.PostItem

@Composable
fun TrendsScreenMock(modifier: Modifier = Modifier, hits: List<HitDTO>) {
    LazyColumn(
        modifier = modifier
    ) {
        items(hits) { story ->
            PostItem(story)
        }
    }
}