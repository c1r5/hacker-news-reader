package dev.eric.hnreader.ui.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import dev.eric.hnreader.ui.themes.HackerNewsTheme.isDarkTheme


val LightColors = lightColorScheme(
    primary = Rose0,
    onPrimary = Neutral2,
    primaryContainer = Rose1,
)

val DarkColors = darkColorScheme(
    primary = Neutral7,
    onPrimary = Neutral5,
    primaryContainer = Neutral8
)

@Composable
fun HackerNewsTheme(content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) {
        DarkColors
    } else {
        LightColors
    }


    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

object HackerNewsTheme {
    val isDarkTheme: Boolean
        @Composable
        get() = isSystemInDarkTheme()
    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme
}