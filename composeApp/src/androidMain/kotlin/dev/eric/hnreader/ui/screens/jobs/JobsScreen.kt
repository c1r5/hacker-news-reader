package dev.eric.hnreader.ui.screens.jobs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.eric.hnreader.koinViewModel
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.ui.components.PostItem
import dev.eric.hnreader.viewmodels.HackerNewsViewModel
import kotlinx.datetime.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(viewModel: HackerNewsViewModel = koinViewModel()) {
    val hits = viewModel.jobs.collectAsLazyPagingItems()
    val isRefreshing = hits.loadState.refresh is LoadState.Loading
    PullToRefreshBox(
        modifier = Modifier.fillMaxWidth(),
        onRefresh = { hits.refresh() },
        isRefreshing = isRefreshing,
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

@Composable
@Preview
fun JobsScreenPreview() {
    val hits = List(10) {}
    LazyColumn {
        items(hits.size) { index ->
            PostItem(HitDTO.StoryHitDTO(
                objectID = "1",
                points = 1687,
                title = "Y Combinator",
                url = "http://ycombinator.com",
                author = "c1r5",
                createdAt = "2017-06-16T13:03:09Z",
                createdAtI = 1497618189,
                updatedAt = "2024-09-20T00:59:22Z",
                numComments = 824,
                storyId = 14568468,
            ))
        }
    }
}