package dev.eric.hnreader.datasources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.dtos.HitDTO
import kotlinx.coroutines.flow.Flow

class HackerNewsRepository(
    private val hackerNewsService: HackerNewsService
) {
    private val pagingSource = HackerNewsPagingSource(hackerNewsService)
    fun paginatedHits(): Flow<PagingData<HitDTO>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 5
        )
    ) { pagingSource }.flow
}