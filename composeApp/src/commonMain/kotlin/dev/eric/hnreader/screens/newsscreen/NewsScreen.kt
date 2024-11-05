package dev.eric.hnreader.screens.newsscreen

import androidx.compose.runtime.Composable
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@Composable
expect fun NewsScreen(viewModel: HackerNewsViewModel)