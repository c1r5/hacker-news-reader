package dev.eric.hnreader.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.PostDTO
import dev.eric.hnreader.util.trendsCalc
import kotlinx.datetime.Instant

class TrendsPagingSource(
    private val service: HackerNewsService
) : PagingSource<Int, PostDTO>() {

    override fun getRefreshKey(state: PagingState<Int, PostDTO>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostDTO> {
        try {
            val defaultSearchPayload = service.defaultPayload.apply {
                page = params.key ?: 0
                tags += SearchTags.FRONTPAGE
            }

            val response = service.search(defaultSearchPayload).getOrThrow()
            val sortedHits = response.hits.sortedByDescending {
                trendsCalc(it.points ?: 0, Instant.parse(it.createdAt).toEpochMilliseconds())
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

