package dev.eric.hnreader

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.eric.hnreader.screens.frontscreen.FrontScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope

const val appTitle = "HN Reader"

@Composable
@Preview
fun App() {
    KoinContext {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "front_screen"
        ) {
            composable("front_screen") {
                FrontScreen(
                    viewModel = koinViewModel()
                )
            }

            composable("news_screen") {}

            composable("jobs_screen") {}
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