package dev.eric.hnreader.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.eric.hnreader.App
import dev.eric.hnreader.koinViewModel
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.ui.screens.jobs.JobsScreen
import dev.eric.hnreader.ui.screens.news.NewsScreen
import dev.eric.hnreader.ui.screens.trends.TrendsScreen
import dev.eric.hnreader.ui.screens.trends.TrendsScreenMock
import dev.eric.hnreader.viewmodels.HackerNewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App {
                AndroidInterface()
            }
        }
    }
}

class TopAppBarItem(
    val title: String,
)

class BottomAppBarItem(
    val icon: ImageVector,
    val label: String
)

sealed class ScreenLayout(
    val topAppBar: TopAppBarItem,
    val bottomAppBar: BottomAppBarItem, ) {
    data object Trends : ScreenLayout(
        topAppBar = TopAppBarItem(
            title = "Top Stories"
        ),
        bottomAppBar = BottomAppBarItem(
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            label = "Top Stories"
        )
    )

    data object News : ScreenLayout(
        topAppBar = TopAppBarItem(
            title = "Hacker News"
        ),
        bottomAppBar = BottomAppBarItem(
            icon = Icons.Rounded.Newspaper,
            label = "News"
        )
    )

    data object Jobs : ScreenLayout(
        topAppBar = TopAppBarItem(
            title = "Jobs"
        ),
        bottomAppBar = BottomAppBarItem(
            icon = Icons.Rounded.Work,
            label = "Jobs"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidInterface(viewModel: HackerNewsViewModel = koinViewModel()) {
    val topics = remember {
        listOf(
            ScreenLayout.Trends,
            ScreenLayout.News,
            ScreenLayout.Jobs
        )
    }

    var currentTopic by remember { mutableStateOf(topics[1]) }

    val pagerState = rememberPagerState {
        topics.size
    }

    LaunchedEffect(currentTopic) {
        pagerState.animateScrollToPage(topics.indexOf(currentTopic))
    }

    LaunchedEffect(pagerState.targetPage) {
        currentTopic = topics[pagerState.targetPage]
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppBarTitle(currentTopic.topAppBar.title) },
                colors = appBarColors()
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ) {
                topics.forEach { screen ->
                    with(screen.bottomAppBar) {
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = null) },
                            label = { Text(label) },
                            selected = currentTopic == screen,
                            onClick = { currentTopic = screen })
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            modifier = Modifier.padding(innerPadding)
        ) { page ->
            val item = topics[page]
            when (item) {
                ScreenLayout.Trends -> TrendsScreen(viewModel)
                ScreenLayout.News -> NewsScreen(viewModel)
                ScreenLayout.Jobs -> JobsScreen(viewModel)
            }
        }
    }
}

@Composable
fun AppBarTitle(title: String) = Text(
    text = title,
    style = MaterialTheme.typography.titleLarge
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors() = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainer,
    titleContentColor = MaterialTheme.colorScheme.inverseSurface
)

@Composable
@Preview
fun AppAndroidPreview() {
    val storyItems by remember {
        mutableStateOf(List(10) {
            HitDTO.StoryHitDTO(
                author = "mock_author",
                children = listOf(123456789L, 987654321L),
                createdAt = "2024-11-01T12:34:56Z",
                createdAtI = 1730359089,
                numComments = 42L,
                objectID = "1234567890",
                points = 100,
                storyId = 1122334455L,
                title = "Mock Title for Story With Overflow Text",
                updatedAt = "2024-11-02T14:20:00Z",
                url = "https://example.com/mock-story-url"
            )
        })
    }

    Scaffold {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            TrendsScreenMock(storyItems)
        }
    }
}


