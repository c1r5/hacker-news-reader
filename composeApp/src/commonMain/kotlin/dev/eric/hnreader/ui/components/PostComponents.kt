package dev.eric.hnreader.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.util.elapsedTime
import dev.eric.hnreader.util.fetchImage
import kotlinx.coroutines.Dispatchers
import java.net.URI


@Composable
fun PostItem(hit: HitDTO) {
    OutlinedCard(
        shape = ShapeDefaults.Medium,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PostUrlPreview(hit.url)
                PostURL(hit.url)
                PostTitle(hit.title)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    hit.points?.let { PostPointsCount(it) }
                    hit.numComments?.let { PostCommentsCount(it) }
                    PostCreatedAt(elapsedTime(hit.createdAtI).toHumanReadable())
                    PostAuthor(hit.author)
                }
            }
        }
    }
}

@Composable
private fun PostUrlPreview(postURL: String?) {
    var imageURL by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        imageURL = fetchImage(postURL, Dispatchers.IO).getOrNull()
    }

    AsyncImage(
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.Low,
        model = imageURL,
        contentDescription = "Photo",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun PostTitle(title: String) {
    Text(
        text = title,
        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
        fontSize = MaterialTheme.typography.titleMedium.fontSize
    )
}

@Composable
private fun PostURL(url: String?) {
    if (url == null) return

    val uri = URI.create(url)
    val baseURL = "${uri.scheme}://${uri.host}"
    val faviconURL = "https://www.google.com/s2/favicons?domain_url=$baseURL&sz=32"

    var isLoading by remember { mutableStateOf(true) }
    val onErrorPainter = rememberVectorPainter(Icons.Outlined.Public)

    val faviconPainter = rememberAsyncImagePainter(
        model = faviconURL,
        error = onErrorPainter,
        placeholder = rememberVectorPainter(Icons.Outlined.Public),
        onLoading = { isLoading = true },
        onSuccess = { isLoading = false },
        onError = { isLoading = false }
    )

    PostMetadata(
        icon = { modifier ->
            Icon(
                modifier = modifier,
                painter = faviconPainter,
                contentDescription = "Favicon Icon",
                tint = Color.Unspecified
            )
        },
        value = uri.host.uppercase()
    )
}

@Composable
private fun PostCreatedAt(timeString: String) {

    PostMetadata(
        icon = { modifier ->
            Icon(
                modifier = modifier,
                imageVector = Icons.Rounded.AccessTime,
                contentDescription = "Time Icon"
            )
        },
        value = timeString
    )
}

@Composable
private fun PostCommentsCount(count: Int) {
    PostMetadata(
        icon = { modifier ->
            Icon(
                modifier = modifier,
                imageVector = Icons.AutoMirrored.Outlined.Comment,
                contentDescription = "Comments Icon"
            )
        },
        value = if (count >= 100) "99+" else "$count"
    )
}

@Composable
private fun PostPointsCount(points: Int) {
    PostMetadata(
        icon = { modifier ->
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Points Icon",
                modifier = modifier.rotate(-90f)
            )
        },
        value = points.toString(),
    )
}

@Composable
private fun PostAuthor(author: String) {
    PostMetadata(
        icon = { modifier ->
            Icon(
                modifier = modifier,
                imageVector = Icons.Rounded.Person,
                contentDescription = "Author Icon"
            )
        },
        value = author
    )
}

@Composable
private fun PostMetadata(icon: @Composable (Modifier) -> Unit, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        icon(Modifier.size(16.dp))
        Text(
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 5.dp),
            text = value
        )
    }
}