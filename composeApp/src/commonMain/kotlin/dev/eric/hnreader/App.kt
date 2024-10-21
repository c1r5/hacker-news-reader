package dev.eric.hnreader

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.eric.hnreader.screens.TopStories
import dev.eric.hnreader.viewmodels.HackerNewsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope

const val appTitle = "HN Reader"

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            val hackerNewsViewModel = koinViewModel<HackerNewsViewModel>()
            NavHost(
                navController = navController,
                startDestination = "topStories"
            ) {
                composable("topStories") {
                    val stories by hackerNewsViewModel.stories.collectAsState()

                    TopStories(stories)
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}