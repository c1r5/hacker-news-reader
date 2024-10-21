package dev.eric.hnreader.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
    val stories by viewModel.stories.collectAsState()

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
            StoryCard(stories[index])
        }

        item {
            if (loadState == StoryLoadState.Loading) {
                LoadingProgressIndicator()
            }
        }
    }
}

@Composable
fun StoryCard(storyView: StoryView) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Row {
                StoryStats(storyView)
                Spacer(modifier = Modifier.width(10.dp))
                Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
                StoryContent(storyView)
            }
        }
    }
}

@Composable
fun StoryContent(storyView: StoryView) {
    Column {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            text = storyView.title.orEmpty()
        )
    }
}

@Composable
fun StoryStats(storyView: StoryView) {
    Column {
        StoryStatsItem(Icons.Filled.KeyboardArrowUp, storyView.scoreCount.toString())
        StoryStatsItem(Icons.Filled.DateRange, storyView.postedAt.orEmpty())
    }
}

@Composable
fun StoryStatsItem(icon: ImageVector, text: String) {
    Column {
        Icon(icon, contentDescription = "Up")
        Text(text)
    }
}


