package dev.eric.hnreader.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.koinViewModel
import dev.eric.hnreader.ui.components.PostItem
import dev.eric.hnreader.viewmodels.TechNewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: TechNewsViewModel = koinViewModel(), onSelectPost: () -> Unit) {
    val hits = viewModel.news.collectAsLazyPagingItems()
    val isRefreshing = hits.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        modifier = Modifier.fillMaxWidth(),
        onRefresh = { hits.refresh() },
        isRefreshing = isRefreshing
    ) {
        LazyColumn {
            items(hits.itemCount) { index ->
                hits[index]?.let { post ->
                    PostItem(post, onPostSelected = {
                        viewModel.setSelectedPost(post)
                        onSelectPost()
                    })
                }
            }
        }
    }
}