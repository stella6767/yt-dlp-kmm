package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun InputSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        PathInputSection(title = "URL", "", 100.dp)
        PathInputSection(title = "File name", "", 100.dp)
        PathInputSection(title = "Save to", "", 100.dp, Icons.Default.FolderOpen)
        // Additional arguments
        PathInputSection(
            title = "Additional arguments",
            placeholder = "",
            200.dp
        )
        // yt-dlp 및 ffmpeg 경로 입력 섹션
        PathInputSection(
            title = "yt-dlp 경로",
            placeholder = "/usr/local/bin/yt-dlp",
            100.dp,
            Icons.Default.FolderOpen
        )
    }
}

