package dev.eric.hnreader.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.eric.hnreader.App
import dev.eric.hnreader.appTitle

const val appBarColor = 0xFFFF8500

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidInterface { App() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidInterface(composable: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = appTitle,
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            fontSize = 18.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(appBarColor),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color(appBarColor)) { }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            composable()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AndroidInterface { App() }
}


