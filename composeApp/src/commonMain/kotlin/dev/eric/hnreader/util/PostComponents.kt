package dev.eric.hnreader.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.eric.hnreader.models.dtos.HitDTO
import kotlinx.datetime.DateTimeUnit

@Composable
fun PostList(itemCount: Int, itemContent: @Composable (Int) -> Unit) {
    LazyColumn {
        items(itemCount) { index ->
            itemContent(index)
        }
    }
}

@Composable
fun PostItem(hit: HitDTO) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PostTitle(hit)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PostPointsCount(hit)
                    PostCommentsCount(hit)
                    PostCreatedAt(hit)
                    PostAuthor(hit)
                }
            }
        }
    }
}

@Composable
private fun PostTitle(hit: HitDTO) {
    val title = hit.title
    Text(
        text = title,
        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
        fontSize = MaterialTheme.typography.titleMedium.fontSize
    )
}

@Composable
private fun PostCreatedAt(hit: HitDTO) {
    val timestamp = hit.createdAtI
    val elapsedTime = elapsedTime(timestamp)
    val timeUnit = when (elapsedTime.unit) {
        DateTimeUnit.SECOND -> "s"
        DateTimeUnit.MINUTE -> "m"
        DateTimeUnit.HOUR -> "h"
        DateTimeUnit.DAY -> "d"
        DateTimeUnit.WEEK -> "w"
        DateTimeUnit.MONTH -> "mo"
        DateTimeUnit.YEAR -> "y"
        else -> {
            "unknown"
        }
    }
    val timeString = "${elapsedTime.time}$timeUnit"

    PostMetadata(
        icon = Icons.Rounded.AccessTime,
        value = timeString
    )
}

@Composable
private fun PostCommentsCount(hit: HitDTO) {
    val commentsCount = when (hit) {
        is HitDTO.AskHitDTO -> {
            hit.numComments
        }

        is HitDTO.StoryHitDTO -> {
            hit.numComments
        }

        else -> null
    }

    commentsCount?.let { count ->
        val countString = if (count >= 100) "99+" else count.toString()
        PostMetadata(
            icon = Icons.AutoMirrored.Outlined.Comment,
            value = countString
        )
    }
}

@Composable
private fun PostPointsCount(hit: HitDTO) {
    hit.points?.let { points ->
        PostMetadata(
            icon = Icons.Rounded.PlayArrow,
            value = points.toString(),
            iconModifier = Modifier.rotate(-90f)
        )
    }
}

@Composable
private fun PostAuthor(hit: HitDTO) {
    val author = hit.author
    PostMetadata(
        icon = Icons.Rounded.Person,
        value = author
    )
}

@Composable
private fun PostMetadata(icon: ImageVector, value: String, iconModifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = iconModifier
        )
        Text(
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
            modifier = Modifier.padding(end = 5.dp),
            text = value
        )
    }
}