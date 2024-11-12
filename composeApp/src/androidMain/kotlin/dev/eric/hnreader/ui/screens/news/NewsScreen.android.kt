package dev.eric.hnreader.ui.screens.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.ui.components.PostItem
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun NewsScreen(viewModel: HackerNewsViewModel) {
    val hits = viewModel.news.collectAsLazyPagingItems()
    val isRefreshing = hits.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        contentAlignment = Alignment.TopCenter,
        onRefresh = {
            hits.refresh()
        },
        isRefreshing = isRefreshing
    ) {
        LazyColumn {
            items(hits.itemCount) { index ->
                hits[index]?.let { hit ->
                    PostItem(hit)
                }
            }
        }
    }
}