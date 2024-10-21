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

    private val _topStoriesIds = MutableStateFlow<Stories>(emptyList())

    private val _totalPages = MutableStateFlow(0)

    private val _currentPageNum = MutableStateFlow(0)

    private val _currentPageStoryIds = MutableStateFlow<Stories>(emptyList())

    private val _stories = MutableStateFlow<List<StoryView>>(emptyList())
    val stories = _stories.asStateFlow()

    init {
        viewModelScope.launch {
            _topStoriesIds.emit(hackerNewsRepository.getTopStories())
        }

        viewModelScope.launch {
            _topStoriesIds.filter { it.isNotEmpty() }.collect { ids ->
                val chunkedList = ids.chunked(10)
                val numPages = chunkedList.size

                _currentPageStoryIds.emit(chunkedList[0])
                _currentPageNum.emit(0)
                _totalPages.emit(numPages)
            }
        }

        viewModelScope.launch {
            _currentPageStoryIds.filter { it.isNotEmpty() }.collect { ids ->
                ids.forEach {
                    val story = hackerNewsRepository.getStory(it) ?: return@forEach
                    val storyView = StoryView.from(story)
                    _stories.emit(_stories.value + storyView)
                }
            }
        }
    }
}