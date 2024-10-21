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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import data.StoryView

@Composable
fun TopStories(stories: List<StoryView> = emptyList()) {
    LazyColumn {
        items(stories.size) { index ->
            StoryCard(stories[index])
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


