package dev.eric.hnreader

import KoinInit
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    KoinInit().init()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Hacker News Reader",
    ) {
        App()
    }
}
