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
fun InputSectionContainer() {

    val viewModel: DownloaderViewModel = koinViewModel<DownloaderViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        TextInputSection(value = uiState.url, title = "URL", "", 100.dp, viewModel::updateUrl)
        TextInputSection(
            value = uiState.fileName,
            title = "File name",
            "leave empty for default name",
            100.dp,
            viewModel::updateFileName)

        // Additional arguments
        TextInputSection(
            value = uiState.additionalArguments,
            title = "Additional arguments",
            placeholder = "",
            200.dp,
            viewModel::updateAdditionalArguments
        )


    }
}

