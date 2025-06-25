package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.component.DownloadLogViewer
import freeapp.me.yt_dlp_gui.presentation.downloader.component.FileSelectableGroup
import freeapp.me.yt_dlp_gui.presentation.downloader.component.InputSectionContainer
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DownloaderScreen(
    viewModel: DownloaderViewModel = koinViewModel<DownloaderViewModel>()
) {

    val uiState by viewModel.uiState.collectAsState()


    // 라디오 버튼 그룹 상태 관리
    val videoOptions = listOf("Video (full)", "Video (partial)")
    var selectedVideoOption by remember { mutableStateOf(videoOptions[0]) }

    val audioFormats = listOf("Default", "MP3")
    var selectedAudioFormat by remember { mutableStateOf(audioFormats[0]) }

    val videoFormats = listOf("Default", "MP4")
    var selectedVideoFormat by remember { mutableStateOf(videoFormats[0]) }

    val stateVertical = rememberScrollState(0)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end =10.dp),
    ) {


        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(stateVertical)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {


            InputSectionContainer()

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                // Audio/Video Radio Buttons
                FileSelectableGroup(selectedVideoOption)
            }

            // Audio format and Video format Radio Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Audio format:", )
                Row(modifier = Modifier.selectableGroup()) {
                    audioFormats.forEach { text ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text == selectedAudioFormat),
                                    onClick = { selectedAudioFormat = text }
                                )
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedAudioFormat),
                                onClick = null // null is used because the row has a clickable modifier
                            )
                            Text(text)
                        }
                    }
                }
                Spacer(Modifier.width(32.dp)) // 간격

                Text("Video format:")
                Row(modifier = Modifier.selectableGroup()) {
                    videoFormats.forEach { text ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text == selectedVideoFormat),
                                    onClick = { selectedVideoFormat = text }
                                )
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedVideoFormat),
                                onClick = null
                            )
                            Text(text)
                        }
                    }
                }
            }



            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(onClick = viewModel::startDownload) { Text("Download") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { /* TODO: Abort Download */ }) { Text("Abort") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { /* TODO: Update yt-dlp */ }) { Text("Update yt-dlp") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { /* TODO: Show About */ }) { Text("About") }
            }


            DownloadLogViewer(
                log = uiState.resultLog,
                modifier = Modifier.fillMaxWidth(),
                onCopyLog = {},
                onClearLog = viewModel::clearLog
            )



        }


        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd) // Align to the right edge of the Box
                .fillMaxHeight()
                .padding(1.dp), // A little padding between list and scrollbar
            adapter = rememberScrollbarAdapter(stateVertical),
            style = LocalScrollbarStyle.current.copy(
                thickness = 10.dp, // 스크롤바 두께
                unhoverColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // 마우스 오버 전 색상
                hoverColor = MaterialTheme.colorScheme.primary // 마우스 오버 시 색상
            )
        )
    }



}

