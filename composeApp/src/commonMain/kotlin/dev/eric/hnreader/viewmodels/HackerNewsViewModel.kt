package dev.eric.hnreader.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.eric.hnreader.models.HackerNewsService
import dev.eric.hnreader.models.SearchTags
import dev.eric.hnreader.models.dtos.HitDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.pow

class HackerNewsViewModel(
    private val service: HackerNewsService,
) : ViewModel() {
    private val _stories = MutableStateFlow<List<HitDTO>>(emptyList())
    val stories = _stories.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()


    fun getFrontPage(page: Int = 0) {
        viewModelScope.launch {
            service.search(
                service.defaultPayload.apply {
                    tags += SearchTags.FRONTPAGE
                    byDate = true
                }
            ).onSuccess {
                val hits = it.hits
                val sortedStories = hits.sortedByDescending {
                    val points = it.points ?: 0
                    val hoursSinceCreation =
                        (System.currentTimeMillis() - it.createdAtI) / 3600000.0
                    calcularTendencia(points, hoursSinceCreation)
                }
                _stories.emit(sortedStories)
            }.onFailure {
                println(it)
            }
        }
    }
}


fun calcularTendencia(votos: Int, horas: Double, gamma: Double = 2.0): Double {
    return votos / (1 + horas).pow(gamma)
}