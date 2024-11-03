package dev.eric.hnreader.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.viewmodels.HackerNewsViewModel
import kotlinx.coroutines.flow.first

class FrontScreenPagingSource(
    private val hackerNewsViewModel: HackerNewsViewModel
) : PagingSource<Int, HitDTO>() {
    override fun getRefreshKey(state: PagingState<Int, HitDTO>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitDTO> {
        val currentPage = hackerNewsViewModel.currentPage.first()
        val response = hackerNewsViewModel.getFrontPage(currentPage)

    }
}