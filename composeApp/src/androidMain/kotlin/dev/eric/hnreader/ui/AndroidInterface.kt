package dev.eric.hnreader.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.eric.hnreader.R
import dev.eric.hnreader.ui.screens.HomeScreen
import dev.eric.hnreader.ui.screens.PostScreen


enum class TechNewsScreen(@StringRes val title: Int, val canNavigateBack: Boolean) {
    Home(title = R.string.app_name, canNavigateBack = false),
    Post(title = R.string.post_screen, canNavigateBack = true)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechNewsTopAppBar(
    navController: NavHostController,
    title: String,
    canNavigateBack: Boolean
) {
    TopAppBar(
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(titleContentColor = MaterialTheme.colorScheme.inverseSurface,),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}

@Composable
fun AndroidInterface(
    navController: NavHostController = rememberNavController()
) {
    var currentScreen by remember { mutableStateOf(TechNewsScreen.Home) }

    Scaffold(
        topBar = { TechNewsTopAppBar(
            navController = navController,
            title = stringResource(currentScreen.title),
            canNavigateBack = currentScreen.canNavigateBack
        ) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TechNewsScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TechNewsScreen.Home.name) {
                currentScreen = TechNewsScreen.Home
                HomeScreen(onSelectPost = {
                    navController.navigate(TechNewsScreen.Post.name)
                })
            }

            composable(TechNewsScreen.Post.name) {
                currentScreen = TechNewsScreen.Post
                PostScreen()
            }
        }
    }
}
