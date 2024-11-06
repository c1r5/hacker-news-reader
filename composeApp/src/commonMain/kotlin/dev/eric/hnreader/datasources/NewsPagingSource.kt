package dev.eric.hnreader.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.util.trendsCalc

class NewsPagingSource(private val service: HackerNewsService) :
    PagingSource<Int, HitDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitDTO> {
        try {
            val defaultSearchPayload = service.defaultPayload.apply {
                page = params.key ?: 0
                tags += SearchTags.STORY
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

    override fun getRefreshKey(state: PagingState<Int, HitDTO>): Int? {
        TODO("Not yet implemented")
    }
}
