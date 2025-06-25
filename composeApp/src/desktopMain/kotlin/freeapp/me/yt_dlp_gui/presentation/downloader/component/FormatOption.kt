package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.domain.model.DownloadType


@Composable
fun FormatOption(
    downloadType: DownloadType,
    function: (String) -> Unit,
) {

    val audioFormats = listOf("Default", "MP3")
    var selectedAudioFormat by remember { mutableStateOf(audioFormats[0]) }

    val videoFormats = listOf("Default", "MP4")
    var selectedVideoFormat by remember { mutableStateOf(videoFormats[0]) }



    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Audio format:")
        Row(modifier = Modifier.selectableGroup()) {
            audioFormats.forEach { text ->
                Row(
                    Modifier
                        .selectable(
                            selected = (text == selectedAudioFormat),
                            onClick = {
                                if (downloadType == DownloadType.AUDIO) {
                                    selectedAudioFormat = text
                                    function(text)
                                }
                            }
                        )
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedAudioFormat),
                        onClick = {
                            selectedAudioFormat = text
                            function(text)
                        },
                        enabled = downloadType == DownloadType.AUDIO
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
                            onClick = {
                                if (downloadType != DownloadType.AUDIO) {
                                    selectedVideoFormat = text
                                    function(text)
                                }
                            }
                        )
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedVideoFormat),
                        onClick = {
                            selectedVideoFormat = text
                            function(text)
                        },
                        enabled = downloadType != DownloadType.AUDIO
                    )
                    Text(text)
                }
            }
        }
    }
}
