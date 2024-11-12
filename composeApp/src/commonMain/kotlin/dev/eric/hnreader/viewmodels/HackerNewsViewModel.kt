package dev.eric.hnreader.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.eric.hnreader.datasources.HackerNewsRepository

class HackerNewsViewModel(
    hackerNewsRepository: HackerNewsRepository
) : ViewModel() {
    val trends = hackerNewsRepository.paginatedTrends()
        .cachedIn(viewModelScope)

    val news = hackerNewsRepository.paginatedNews()
        .cachedIn(viewModelScope)

    val jobs = hackerNewsRepository.paginatedJobs()
        .cachedIn(viewModelScope)
}