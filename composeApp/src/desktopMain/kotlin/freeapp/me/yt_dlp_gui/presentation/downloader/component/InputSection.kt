package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun InputSection() {

    val viewModel: DownloaderViewModel = koinViewModel<DownloaderViewModel>()

    val uiState by viewModel.uiState.collectAsState()



    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        PathInputSection(value = uiState.url, title = "URL", "", 100.dp, viewModel::updateUrl)
        PathInputSection(value = uiState.fileName, title = "File name", "", 100.dp, viewModel::updateFileName)
        PathInputSection(value = uiState.saveToDirectory, title = "Save to", "", 100.dp, viewModel::updateSaveToDirectory, Icons.Default.FolderOpen, )
        // Additional arguments
        PathInputSection(
            value = uiState.additionalArguments,
            title = "Additional arguments",
            placeholder = "",
            200.dp,
            viewModel::updateAdditionalArguments
        )
        // yt-dlp 및 ffmpeg 경로 입력 섹션
        PathInputSection(
            value = uiState.ytdlpPath,
            title = "yt-dlp 경로",
            placeholder = "/usr/local/bin/yt-dlp",
            100.dp,
            viewModel::updateYTDlpPath,
            Icons.Default.FileOpen,
        )

    }
}

