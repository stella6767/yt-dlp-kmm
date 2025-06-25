package freeapp.me.yt_dlp_gui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderScreen
import freeapp.me.yt_dlp_gui.presentation.downloader.component.DownloadFolderSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.PathInputSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.UrlInputSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.YtdlpOptionsSection
import freeapp.me.yt_dlp_gui.presentation.layout.MTopAppBar
import freeapp.me.yt_dlp_gui.presentation.layout.Sidebar
import freeapp.me.yt_dlp_gui.util.DarkColorPalette
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


@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize() // 화면 전체 채우기
            .background(Color.White) // 배경색 흰색
            .padding(paddingValues) // Scaffold의 패딩 적용
            .padding(16.dp), // 추가 패딩
        horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬
    ) {
        // 각 섹션 간의 간격
        Spacer(Modifier.height(16.dp))

        // yt-dlp 및 ffmpeg 경로 입력 섹션
        PathInputSection(
            title = "yt-dlp 경로",
            placeholder = "/usr/local/bin/yt-dlp",
            100.dp
        )
        Spacer(Modifier.height(16.dp))
        PathInputSection(
            title = "FFmpeg 경로 (선택 사항)",
            placeholder = "/usr/local/bin/ffmpeg",
            100.dp
        )

        Spacer(Modifier.height(24.dp))

        // 동영상 URL 입력 섹션
        UrlInputSection()

        Spacer(Modifier.height(24.dp))

        // 다운로드 폴더 지정 섹션
        DownloadFolderSection()

        Spacer(Modifier.height(24.dp))

        // yt-dlp 옵션 입력 섹션
        YtdlpOptionsSection()

        Spacer(Modifier.height(32.dp))

        // 다운로드 버튼
        Button(
            onClick = {
                // 실제 다운로드 로직은 여기에 구현되지 않습니다.
                // 이 버튼은 UI 목업을 위한 것입니다.
                println("다운로드 버튼 클릭됨!")
            },
            modifier = Modifier
                .fillMaxWidth(0.6f) // 너비는 부모의 60%
                .height(56.dp), // 높이 설정
            shape = RoundedCornerShape(12.dp), // 둥근 모서리
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) // 버튼 색상
        ) {
            Text(
                "다운로드 시작",
                color = Color.White, // 텍스트 색상 흰색
                style = MaterialTheme.typography.headlineMedium // 텍스트 스타일
            )
        }
    }
}
