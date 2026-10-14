package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppDestination
import com.example.myapplication.player.DiscoverScreen
import com.example.myapplication.player.DownloadsScreen
import com.example.myapplication.player.LibraryScreen
import com.example.myapplication.player.MockLibrary
import com.example.myapplication.player.NowPlayingBar
import com.example.myapplication.player.PlayerController
import com.example.myapplication.ui.theme.ThemeMode
import kotlinx.coroutines.delay

@Composable
fun PodcastApp(
    themeMode: ThemeMode,
    onToggleTheme: () -> Unit
) {
    val navController: NavHostController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableStateOf(AppDestination.LIBRARY) }

    var nowPlayingId by rememberSaveable { mutableStateOf<String?>(null) }
    val nowPlayingItem = remember(nowPlayingId) {
        MockLibrary.items.firstOrNull { it.id == nowPlayingId }
    }

    val context = LocalContext.current
    val playerController = remember { PlayerController(context) }
    DisposableEffect(Unit) {
        onDispose { playerController.release() }
    }
    LaunchedEffect(playerController) {
        while (true) {
            delay(200)
            playerController.syncPosition()
        }
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestination.entries.forEach { destination ->
                item(
                    selected = destination == selectedDestination,
                    onClick = {
                        selectedDestination = destination
                        navController.navigate(destination.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (destination == selectedDestination) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) }
                )
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = AppDestination.LIBRARY.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(AppDestination.LIBRARY.route) {
                    LibraryScreen(
                        items = MockLibrary.items,
                        nowPlayingId = nowPlayingId,
                        themeMode = themeMode,
                        onToggleTheme = onToggleTheme,
                        onItemSelected = { item ->
                            if (nowPlayingId == item.id) {
                                playerController.togglePlayPause()
                            } else {
                                nowPlayingId = item.id
                                playerController.play(item.id, R.raw.sample_track)
                            }
                        }
                    )
                }
                composable(AppDestination.DISCOVER.route) {
                    DiscoverScreen()
                }
                composable(AppDestination.DOWNLOADS.route) {
                    DownloadsScreen()
                }
            }

            nowPlayingItem?.let { item ->
                NowPlayingBar(
                    item = item,
                    isPlaying = playerController.isPlaying,
                    progressFraction = playerController.progressFraction,
                    onTogglePlaying = { playerController.togglePlayPause() },
                    onSeek = { fraction -> playerController.seekTo(fraction) }
                )
            }
        }
    }
}
