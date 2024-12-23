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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Public
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.util.elapsedTime
import dev.eric.hnreader.util.fetchImage
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.net.URI

@Preview
@Composable
fun PostItem(hit: HitDTO, postActions: PostActions? = null) {
    OutlinedCard(
        shape = ShapeDefaults.Medium,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                hit.url?.let {
                    PostUrlPreview(it)
                    PostURL(it)
                }

                PostTitle(hit.title)

                Row (
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    hit.points?.let { PostPointsCount(it) }
                    hit.numComments?.let { PostComments(it) }
                    PostAuthor(hit.author)
                    PostCreated(elapsedTime(hit.createdAtI).toHumanReadable())
                }
            }
        }
    }
}

interface PostActions {
    fun onPostClick() {}
    fun onSaveButtonClick() {}
    fun onShareClick() {}
    fun onUrlViewClick() {}
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

    Row (
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = faviconPainter,
            contentDescription = "Favicon Icon",
            tint = Color.Unspecified
        )

        Text(
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 5.dp),
            text = uri.host.uppercase()
        )
    }
}

@Composable
private fun PostPointsCount(points: Int) {

}

@Composable
private fun PostAuthor(author: String) {
    Post(
        imageVector = Icons.Filled.Person,
        value = author
    )
}

@Composable
private fun PostCreated(timeString: String) {
    Post(
        imageVector = Icons.Default.Schedule,
        value = timeString
    )
}

@Composable
private fun PostComments(count: Int) {
    Post(
        imageVector = Icons.AutoMirrored.Outlined.Comment,
        value = "$count"
    )
}

@Composable
private fun Post(imageVector: ImageVector, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically // Centraliza verticalmente
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(16.dp) // Ajuste o tamanho conforme necessário
        )

        Text(
            text = value,
            textAlign = TextAlign.Center
        )
    }
}