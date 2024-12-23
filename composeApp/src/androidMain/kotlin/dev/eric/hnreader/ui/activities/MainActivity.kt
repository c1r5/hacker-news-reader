package dev.eric.hnreader.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.eric.hnreader.App
import dev.eric.hnreader.koinViewModel
import dev.eric.hnreader.ui.models.TabLayout
import dev.eric.hnreader.ui.screens.jobs.JobsScreen
import dev.eric.hnreader.ui.screens.news.NewsScreen
import dev.eric.hnreader.ui.screens.trends.TrendsScreen
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App {

                AndroidInterface()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidInterface() {
    val tabs = remember {
        listOf(
            TabLayout.Trends,
            TabLayout.News,
            TabLayout.Jobs
        )
    }
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

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.inverseSurface,
                ),
                title = {
                    Text(
                        text = "Hacker News",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = tabIndex
            ) {
                tabs.forEach { screen ->
                    with(screen.tabItem) {
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
                    TabLayout.Trends -> TrendsScreen()
                    TabLayout.News -> NewsScreen()
                    TabLayout.Jobs -> JobsScreen()
                }
            }
        }

    }
}




