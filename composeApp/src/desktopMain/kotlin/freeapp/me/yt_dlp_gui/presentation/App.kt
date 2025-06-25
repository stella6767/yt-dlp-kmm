package freeapp.me.yt_dlp_gui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderScreen
import freeapp.me.yt_dlp_gui.presentation.downloader.component.DownloadFolderSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.PathInputSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.UrlInputSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.YtdlpOptionsSection
import freeapp.me.yt_dlp_gui.presentation.layout.MTopAppBar
import freeapp.me.yt_dlp_gui.presentation.layout.Sidebar
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    var isSidebarExpanded by remember { mutableStateOf(true) } // State for sidebar expansion
    //val windowSize = LocalWindowInfo.current.containerSize

    MaterialTheme(
        colorScheme = darkColorScheme(),
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
    ) {
        Scaffold(
            topBar = { MTopAppBar( { isSidebarExpanded = !isSidebarExpanded }) }
        ) { paddingValues -> // Scaffold가 제공하는 패딩 값을 받습니다.
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Scaffold의 TopAppBar 아래에 콘텐츠가 오도록 패딩 적용
            ) {
                // 사이드바 영역
                AnimatedVisibility(
                    visible = isSidebarExpanded,
                    enter = expandHorizontally(expandFrom = Alignment.Start, animationSpec = tween(durationMillis = 200)),
                    exit = shrinkHorizontally(shrinkTowards = Alignment.Start, animationSpec = tween(durationMillis = 200))
                ) {
                    Sidebar(
                        modifier = Modifier
                            .width(200.dp) // 사이드바 너비
                            .fillMaxHeight()
                            .background(Color.Gray) // Light grey background
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
                    DownloaderScreen()
                }
            }
        }
    }

}

