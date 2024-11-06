package dev.eric.hnreader.ui.screens.news

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.util.PostItem
import dev.eric.hnreader.util.PostList
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun NewsScreen(viewModel: HackerNewsViewModel) {
    val hits = viewModel.news.collectAsLazyPagingItems()
    val isRefreshing = hits.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        onRefresh = { hits.refresh() },
        isRefreshing = isRefreshing
    ) {
        Box {
            PostList(hits.itemCount) { index ->
                hits[index]?.let { hit ->
                    PostItem(hit)
                }
            }
        }
    }
}