package dev.eric.hnreader.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import data.StoryView
import dev.eric.hnreader.koinViewModel
import dev.eric.hnreader.util.LoadingProgressIndicator
import dev.eric.hnreader.util.infiniteList
import dev.eric.hnreader.viewmodels.HackerNewsViewModel
import dev.eric.hnreader.viewmodels.StoryLoadState

@Composable
fun TopStories(viewModel: HackerNewsViewModel = koinViewModel()) {
    val loadState by viewModel.storyLoadState.collectAsState()
    val stories by viewModel.storiesView.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.nextStories()
    }

    LazyColumn {
        infiniteList(
            stories.size,
            onReachEnd = {
                if (loadState != StoryLoadState.Loading) {
                    LaunchedEffect(Unit) {
                        viewModel.setStoryLoadState(StoryLoadState.Loading)
                        viewModel.nextStories()
                        viewModel.increasePage()
                    }
                }
            }
        ) { index ->
            StoryCard(stories[index]) {
                viewModel.onStoryClick(it)
            }
        }

        item {
            if (loadState == StoryLoadState.Loading) {
                LoadingProgressIndicator()
            }
        }
    }
}

@Composable
fun StoryListMock(stories: List<StoryView>) {
    LazyColumn {
        items(stories.size) { index ->
            StoryCard(stories[index])
        }
    }
}

@Composable
fun StoryCard(storyView: StoryView, onCardClick: (StoryView) -> Unit = {}) {
    OutlinedCard(
        onClick = {
            onCardClick(storyView)
        },
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Column {
                StoryTitle(storyView)
                Spacer(Modifier.height(20.dp))
                StoryStats(storyView)
            }
        }
    }
}

@Composable
fun StoryTitle(storyView: StoryView) {
    Column {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            text = storyView.title.orEmpty(),
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StoryStats(storyView: StoryView) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

    }
}

@Composable
fun StoryStatsItem(
    icon: ImageVector,
    label: String,
    storyView: StoryView,
    viewModel: HackerNewsViewModel = koinViewModel()
) {
    Card(
        onClick = {}
    ) {
        Row {
            Icon(imageVector = icon, contentDescription = null)
            Text(text = label)
        }
    }
}


