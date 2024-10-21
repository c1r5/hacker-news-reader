package dev.eric.hnreader.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.HackerNewsRepository
import data.Stories
import data.StoryView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HackerNewsViewModel(
    private val hackerNewsRepository: HackerNewsRepository
) : ViewModel() {

    private val _stories = MutableStateFlow<List<StoryView>>(emptyList())
    val stories = _stories.asStateFlow()

    private val _storyLoadState = MutableStateFlow<StoryLoadState?>(null)
    val storyLoadState = _storyLoadState.asStateFlow()

    private val _chunkedStories = MutableStateFlow<List<Stories>>(emptyList())
    private val _currentPageIndex = MutableStateFlow(0)

    init {
        getTopStories()
    }


    private fun getTopStories() = viewModelScope.launch {
        val topStories = hackerNewsRepository.getTopStories()
        val chunkedStories = topStories.chunked(10)
        _chunkedStories.emit(chunkedStories)
    }

    fun nextStories() {
        setStoryLoadState(StoryLoadState.Loading)
        viewModelScope.launch {
            _chunkedStories.filter { it.isNotEmpty() }.collect { stories ->
                val storiesView = stories[_currentPageIndex.value].mapNotNull { storyId ->
                    val story = hackerNewsRepository.getStory(storyId)
                    StoryView.from(story)
                }
                _stories.emit((_stories.value + storiesView).distinct())
                setStoryLoadState(StoryLoadState.Success)
            }
        }
    }

    fun setStoryLoadState(state: StoryLoadState) {
        viewModelScope.launch {
            _storyLoadState.emit(state)
        }
    }

    fun increasePage() {
        viewModelScope.launch {
            _currentPageIndex.emit(_currentPageIndex.value + 1)
        }
    }
}

sealed class StoryLoadState {
    data object Loading : StoryLoadState()
    data class Error(val message: String) : StoryLoadState()
    data object Success : StoryLoadState()
}