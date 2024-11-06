package dev.eric.hnreader.screens.trends

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.util.PostItem
import dev.eric.hnreader.util.PostList
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@Composable
actual fun TrendsScreen(viewModel: HackerNewsViewModel) {
    val hits = viewModel.trends.collectAsLazyPagingItems()

    PostList(hits.itemCount) { index ->
        hits[index]?.let { hit ->
            PostItem(hit)
        }
    }
}