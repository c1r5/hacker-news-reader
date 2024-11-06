package dev.eric.hnreader.screens.jobs

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.util.PostItem
import dev.eric.hnreader.util.PostList
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@Composable
actual fun JobsScreen(viewModel: HackerNewsViewModel) {
    val hits = viewModel.jobs.collectAsLazyPagingItems()

    PostList(hits.itemCount) { index ->
        hits[index]?.let { hit ->
            PostItem(hit)
        }
    }
}