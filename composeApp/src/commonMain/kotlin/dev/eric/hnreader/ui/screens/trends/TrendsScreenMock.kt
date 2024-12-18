package dev.eric.hnreader.ui.screens.trends

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.ui.components.PostItem

@Composable
fun TrendsScreenMock(hits: List<HitDTO>) {
    LazyColumn {
        items(hits) { hit ->
            PostItem(hit)
        }
    }
}