package dev.eric.hnreader.ui.activities

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import dev.eric.hnreader.App
import dev.eric.hnreader.appTitle
import dev.eric.hnreader.models.dtos.HitDTO
import dev.eric.hnreader.screens.StoryList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidInterface { App() }
        }
    }
}
data class NavItem(
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidInterface(composable: @Composable () -> Unit) {
    val navItems = listOf(
        NavItem(
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            label = "Top Stories"
        ),
    )

    var selectedItem by remember { mutableStateOf(navItems.first()) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = appTitle,
                            style = TextStyle.Default.copy(
                                fontSize = 18.sp
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        titleContentColor = MaterialTheme.colorScheme.inverseSurface
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    navItems.forEach {navItem ->
                        NavigationBarItem(
                            selected = navItem == selectedItem,
                            onClick = {
                                selectedItem = navItem
                            },
                            icon = {
                                Icon(navItem.icon, contentDescription = null)
                            },

                            label = {
                                Text(navItem.label)
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                composable()
            }
        }
    }
}

@Composable
@androidx.compose.ui.tooling.preview.Preview
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
    AndroidInterface {
        StoryList(storyItems)
    }
}


