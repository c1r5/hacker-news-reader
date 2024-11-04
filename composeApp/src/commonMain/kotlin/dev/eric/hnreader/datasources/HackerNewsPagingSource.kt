package dev.eric.hnreader.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.util.calcTrend

class HackerNewsPagingSource(private val service: HackerNewsService) : PagingSource<Int, HitDTO>() {

    override fun getRefreshKey(state: PagingState<Int, HitDTO>): Int? {
        TODO("Implement refresh key")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitDTO> {
        try {
            val defaultSearchPayload = service.defaultPayload.apply {
                tags += SearchTags.FRONTPAGE
                byDate = true
                page = params.key ?: 0
            }
            val response = service.search(defaultSearchPayload).getOrThrow()
            val sortedHits = response.hits.sortedByDescending {
                calcTrend(it.points ?: 0, it.createdAtI)
            }
            return LoadResult.Page(
                data = sortedHits,
                nextKey = response.page + 1,
                prevKey = if (response.page == 0 || response.hits.isEmpty()) null else response.page - 1,
            )
        } catch (e: Exception) {
            println(e)
            return LoadResult.Error(e)
        }
    }

}

