package dev.eric.hnreader.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.eric.hnreader.datasources.HackerNewsRepository
import dev.eric.hnreader.models.dtos.PostDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TechNewsViewModel(
    hackerNewsRepository: HackerNewsRepository
) : ViewModel() {
    private val _selectedPost = MutableStateFlow<PostDTO?>(null)
    val selectedPost = _selectedPost.asStateFlow()

    val trends = hackerNewsRepository.paginatedTrends()
        .cachedIn(viewModelScope)

    val news = hackerNewsRepository.paginatedNews()
        .cachedIn(viewModelScope)

    val jobs = hackerNewsRepository.paginatedJobs()
        .cachedIn(viewModelScope)


    fun setSelectedPost(postDTO: PostDTO) = _selectedPost.update { postDTO }
}
