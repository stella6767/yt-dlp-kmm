package freeapp.me.yt_dlp_gui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.rememberWindowState
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
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import yt_dlp_gui.composeapp.generated.resources.Res
import yt_dlp_gui.composeapp.generated.resources.github
import kotlin.system.exitProcess


@Composable
@Preview
fun App(
    exitApplication: () -> Unit
) {

    var isSidebarExpanded by remember { mutableStateOf(true) } // State for sidebar expansion
    val settingViewModel: SettingViewModel = getKoin().get()

    val uiState by settingViewModel.uiState.collectAsState()

    val theme = if (uiState.settingState.isDarkTheme) {
        DarkTheme
    } else {
        LightTheme
    }


    val navController = rememberNavController()

    JBWindow(
        onCloseRequest = exitApplication,
        title = "yt_dlp_gui",
        theme = theme,
        state = rememberWindowState(width = 1200.dp, height = 700.dp),
        mainToolBar = {
            MTopAppBar({ isSidebarExpanded = !isSidebarExpanded })
        }
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
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
                        .fillMaxHeight(),
                    navController
                )
            }

            // 메인 콘텐츠 영역
            Column(
                modifier = Modifier
                    .weight(1f) // 남은 공간 모두 차지
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
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

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
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
