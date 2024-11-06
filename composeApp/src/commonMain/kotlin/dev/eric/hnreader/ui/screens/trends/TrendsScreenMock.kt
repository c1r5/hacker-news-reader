package dev.eric.hnreader.ui.screens.trends

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.util.PostItem
import dev.eric.hnreader.util.PostList

@Composable
fun TrendsScreenMock(hits: List<HitDTO>) {
    PostList(hits.size) {
        PostItem(hits[it])
    }
}