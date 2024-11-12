package dev.eric.hnreader

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.eric.hnreader.ui.themes.HackerNewsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope


@Composable
@Preview
fun App(platformInterface: @Composable () -> Unit) {
    KoinContext {
        HackerNewsTheme {
            platformInterface()
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