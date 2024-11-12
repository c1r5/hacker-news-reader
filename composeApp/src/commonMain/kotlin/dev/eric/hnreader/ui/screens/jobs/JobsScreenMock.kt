package dev.eric.hnreader.ui.screens.jobs

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.ui.components.PostItem

@Composable
fun JobScreenMock(hits: List<HitDTO.JobHitDTO>) {
    LazyColumn {
        items(hits.size) { index ->
            PostItem(hits[index])
        }
    }
}