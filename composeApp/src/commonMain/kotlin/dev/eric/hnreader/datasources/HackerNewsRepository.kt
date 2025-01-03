package dev.eric.hnreader.datasources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.dtos.PostDTO
import kotlinx.coroutines.flow.Flow

class HackerNewsRepository(
    private val hackerNewsService: HackerNewsService
) {

    private val defaultConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false,
        prefetchDistance = 5
    )

    fun paginatedTrends(): Flow<PagingData<PostDTO>> = Pager(
        config = defaultConfig
    ) { TrendsPagingSource(hackerNewsService) }.flow

    fun paginatedNews(): Flow<PagingData<PostDTO>> = Pager(
        config = defaultConfig
    ) { NewsPagingSource(hackerNewsService) }.flow

    fun paginatedJobs(): Flow<PagingData<PostDTO>> = Pager(
        config = defaultConfig
    ) { JobsPagingSource(hackerNewsService) }.flow
}