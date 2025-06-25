package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.component.FileSelectableGroup
import freeapp.me.yt_dlp_gui.presentation.downloader.component.InputSection
import freeapp.me.yt_dlp_gui.presentation.downloader.component.PathInputSection


@Composable
fun DownloaderScreen() {
    // 폼 입력 필드의 상태 관리
    var urlText by remember { mutableStateOf("") }
    var fileNameText by remember { mutableStateOf("") }
    var saveToPath by remember { mutableStateOf("C:\\Users\\espyy\\Downloads\\yt-dlp") } // 기본값
    var additionalArgs by remember { mutableStateOf("") }

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


            InputSection()

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
                Button(onClick = { /* TODO: Start Download */ }) { Text("Download") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { /* TODO: Abort Download */ }) { Text("Abort") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { /* TODO: Update yt-dlp */ }) { Text("Update yt-dlp") }
                Spacer(Modifier.width(16.dp))
                Button(onClick = { /* TODO: Show About */ }) { Text("About") }
            }



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    //.weight(1f) // 남은 공간 모두 차지
                    .border(1.dp, Color.LightGray) // 테두리
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                // 여기에 다운로드 상태 메시지 등이 표시될 것입니다.
                Text("Download log will appear here...")
            }


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

