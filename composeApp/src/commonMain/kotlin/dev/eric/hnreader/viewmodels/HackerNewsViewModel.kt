package dev.eric.hnreader.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.eric.hnreader.datasources.HackerNewsRepository

class HackerNewsViewModel(
    private val hackerNewsRepository: HackerNewsRepository
) : ViewModel() {
    val hits = hackerNewsRepository.paginatedHits()
        .cachedIn(viewModelScope)


}