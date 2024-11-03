package dev.eric.hnreader.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.HitDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HackerNewsViewModel(
    private val service: HackerNewsService,
) : ViewModel() {
    private val _stories = MutableStateFlow<List<HitDTO>>(emptyList())
    val stories = _stories.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()


    fun getFrontPage() {
        viewModelScope.launch {
            service.search(
                service.defaultPayload.apply {
                    tags += SearchTags.FRONTPAGE
                    byDate = true
                }
            ).onSuccess {
                val hits = it.hits
                _stories.emit(hits)
            }.onFailure {
                println(it)
            }
        }
    }
}

