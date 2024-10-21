package dev.eric.hnreader.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun LazyListScope.infiniteList(
    itemSize: Int,
    onReachEnd: @Composable () -> Unit,
    itemContent: @Composable (index: Int) -> Unit
) {
    items(itemSize) {
        itemContent(it)

        if (it == itemSize - 1) {
            onReachEnd()
        }
    }
}

@Composable
fun LoadingProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp).fillMaxWidth().wrapContentSize(Alignment.Center),
        strokeWidth = 2.dp
    )
}