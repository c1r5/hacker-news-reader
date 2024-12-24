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

    companion object {
        fun getMockPost() = PostDTO.StoryPostDTO(
            objectID = "1",
            points = 1687,
            title = "Y Combinator",
            url = "http://ycombinator.com",
            author = "c1r5",
            createdAt = "2017-06-16T13:03:09Z",
            createdAtI = 1497618189,
            updatedAt = "2024-09-20T00:59:22Z",
            numComments = 824,
            storyId = 14568468,
        )
    }
}
