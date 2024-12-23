package dev.eric.hnreader.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.eric.hnreader.ui.models.TabLayout

@Composable
fun HomeScreen(
    onSelectPost: () -> Unit
) {
    val tabs = remember { listOf(TabLayout.Trends, TabLayout.News, TabLayout.Jobs) }
    var tabIndex by remember { mutableIntStateOf(0) }
    var currentTab by remember { mutableStateOf(tabs[tabIndex]) }
    val pagerState = rememberPagerState { tabs.size }

    LaunchedEffect(currentTab) {
        tabIndex = tabs.indexOf(currentTab)
        pagerState.animateScrollToPage(tabIndex)
    }

    LaunchedEffect(pagerState.targetPage) {
        currentTab = tabs[pagerState.targetPage]
    }

    Column {
        TabRow(
            selectedTabIndex = tabIndex
        ) {
            tabs.forEach { screen ->
                screen.tabItem.run {
                    Tab(
                        selectedContentColor = MaterialTheme.colorScheme.inverseSurface,
                        unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                        text = { Text(text = title) },
                        selected = currentTab == screen,
                        onClick = {
                            tabIndex = tabs.indexOf(screen)
                            currentTab = screen
                        }
                    )
                }
            }
        }

        HorizontalPager(pagerState) { page ->
            val item = tabs[page]
            when (item) {
                TabLayout.Trends -> TrendsScreen(onSelectPost = onSelectPost)
                TabLayout.News -> NewsScreen(onSelectPost = onSelectPost)
                TabLayout.Jobs -> JobsScreen(onSelectPost = onSelectPost)
            }
        }
    }
}