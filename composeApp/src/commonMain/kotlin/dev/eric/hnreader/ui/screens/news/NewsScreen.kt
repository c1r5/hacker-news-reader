package dev.eric.hnreader.ui.screens.news

import androidx.compose.runtime.Composable
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

@Composable
expect fun NewsScreen(viewModel: HackerNewsViewModel)