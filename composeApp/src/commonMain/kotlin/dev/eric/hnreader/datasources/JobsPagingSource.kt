package dev.eric.hnreader.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.PostDTO

class JobsPagingSource(private val service: HackerNewsService) :
    PagingSource<Int, PostDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostDTO> {
        try {
            val defaultSearchPayload = service.defaultPayload.apply {
                byDate = true
                page = params.key ?: 0
                tags += SearchTags.JOB
            }

            val response = service.search(defaultSearchPayload).getOrThrow()

            return LoadResult.Page(
                data = response.hits,
                nextKey = response.page + 1,
                prevKey = if (response.page == 0 || response.hits.isEmpty()) null else response.page - 1,
            )
        } catch (e: Exception) {
            println(e)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostDTO>): Int? = null
}
