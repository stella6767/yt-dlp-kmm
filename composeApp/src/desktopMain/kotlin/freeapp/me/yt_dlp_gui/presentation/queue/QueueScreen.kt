package freeapp.me.yt_dlp_gui.presentation.queue

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import freeapp.me.yt_dlp_gui.presentation.downloader.component.DownloadLogViewer
import freeapp.me.yt_dlp_gui.presentation.downloader.component.FileSelectableGroup
import freeapp.me.yt_dlp_gui.presentation.downloader.component.FormatOption
import freeapp.me.yt_dlp_gui.presentation.downloader.component.InputSectionContainer
import freeapp.me.yt_dlp_gui.presentation.queue.component.DownloadList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun QueueScreen(
    viewModel: DownloaderViewModel = koinViewModel<DownloaderViewModel>()
) {

    val uiState by viewModel.uiState.collectAsState()
    val stateVertical = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(stateVertical)
                    .padding(start = 10.dp, end = 30.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {


            InputSectionContainer()

            // Audio/Video Radio Buttons
            FileSelectableGroup(
                uiState.downloadType,
                viewModel::updateDownloadType,
            )

            // Audio format and Video format Radio Buttons
            FormatOption(
                uiState.downloadType,
                viewModel::updateFormat
            )

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = viewModel::startDownload,
                    enabled = !uiState.isDownloading,
                ) {
                    Text("Download")
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = viewModel::abortDownload,
                    enabled = uiState.isDownloading,
                ) {
                    Text("Abort")
                }
                Spacer(Modifier.width(16.dp))
                //Button(onClick = { /* TODO: Update yt-dlp */ }) { Text("Update yt-dlp") }
            }



//            DownloadList(
//                downloadItems = currentDownloadItems,
//                onItemCheckedChanged = { item, isChecked ->
//                    viewModel.updateDownloadItemSelection(item, isChecked)
//                },
//                modifier = Modifier.fillMaxWidth()
//            )




        }


        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd) // Align to the right edge of the Box
                .fillMaxHeight()
                .padding(0.dp), // A little padding between list and scrollbar
            adapter = rememberScrollbarAdapter(stateVertical),
            style = LocalScrollbarStyle.current.copy(
                thickness = 10.dp, // 스크롤바 두께
                unhoverColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // 마우스 오버 전 색상
                hoverColor = MaterialTheme.colorScheme.primary // 마우스 오버 시 색상
            )
        )
    }


}
