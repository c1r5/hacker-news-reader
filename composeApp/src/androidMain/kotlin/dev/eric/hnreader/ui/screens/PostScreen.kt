package dev.eric.hnreader.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.eric.hnreader.models.dtos.PostDTO
import dev.eric.hnreader.ui.themes.TechNewsTheme
import dev.eric.hnreader.viewmodels.TechNewsViewModel

@Composable
fun PostScreen(post: PostDTO?) {
    post?.run {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ){
            Column {
                Text(
                    text = title
                )
            }
        }
    }
}

@Composable
@Preview()
fun PostScreenPreview() {
    TechNewsTheme {
        PostScreen(TechNewsViewModel.getMockPost())
    }
}