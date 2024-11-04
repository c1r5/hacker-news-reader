package dev.eric.hnreader.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@Composable
actual fun FrontScreen(viewModel: HackerNewsViewModel) {
    val hits = viewModel.hits.collectAsLazyPagingItems()
    
    LazyColumn {
        items(hits.itemCount) { index ->
            hits[index]?.let { hit ->
                NewsItem(hit)
            }
        }
    }
}