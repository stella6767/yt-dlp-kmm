package freeapp.me.yt_dlp_gui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderScreen
import freeapp.me.yt_dlp_gui.presentation.global.component.MTopAppBar
import freeapp.me.yt_dlp_gui.presentation.global.component.Sidebar
import freeapp.me.yt_dlp_gui.presentation.queue.QueueScreen
import freeapp.me.yt_dlp_gui.presentation.setting.SettingScreen
import freeapp.me.yt_dlp_gui.presentation.setting.SettingViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    var isSidebarExpanded by remember { mutableStateOf(true) } // State for sidebar expansion


    val settingViewModel: SettingViewModel = koinViewModel<SettingViewModel>()
    val uiState by settingViewModel.uiState.collectAsState()

    val colorScheme = if (uiState.settingState.isDarkTheme){
        darkColorScheme()
    } else{
        lightColorScheme()
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
    ) {

        val navController = rememberNavController()

        Scaffold(
            topBar = { MTopAppBar({ isSidebarExpanded = !isSidebarExpanded }) }
        ) { paddingValues -> // Scaffold가 제공하는 패딩 값을 받습니다.
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Scaffold의 TopAppBar 아래에 콘텐츠가 오도록 패딩 적용
            ) {
                // 사이드바 영역
                AnimatedVisibility(
                    visible = isSidebarExpanded,
                    enter = expandHorizontally(
                        expandFrom = Alignment.Start,
                        animationSpec = tween(durationMillis = 200)
                    ),
                    exit = shrinkHorizontally(
                        shrinkTowards = Alignment.Start,
                        animationSpec = tween(durationMillis = 200)
                    )
                ) {
                    Sidebar(
                        modifier = Modifier
                            .width(150.dp) // 사이드바 너비
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        navController
                    )
                }

                // 메인 콘텐츠 영역
                Column(
                    modifier = Modifier
                        .weight(1f) // 남은 공간 모두 차지
                        .fillMaxHeight(),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.Queue
                    ) {
                        composable<Route.Setting> {
                            SettingScreen(settingViewModel)
                        }

                        composable<Route.Queue> {
                            QueueScreen()
                        }

                        composable<Route.Downloader> {
                            DownloaderScreen()
                        }
                    }


                }
            }
        }
    }

}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
