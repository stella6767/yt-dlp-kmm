package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.component.DownloadLogViewer
import freeapp.me.yt_dlp_gui.presentation.global.component.TextInputSection
import freeapp.me.yt_dlp_gui.presentation.global.component.FileSelectableGroup
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DownloaderScreen(
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
                    viewModel::updateFileName
                )

                // Additional arguments
                TextInputSection(
                    value = uiState.additionalArguments,
                    title = "Additional arguments",
                    placeholder = "",
                    200.dp,
                    viewModel::updateAdditionalArguments
                )
            }



            // Audio/Video Radio Buttons
            FileSelectableGroup(
                uiState.downloadType,
                viewModel::updateDownloadType,
                uiState.startTime,
                uiState.endTime,
                viewModel::updateStartTime,
                viewModel::updateEndTime
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
            }


            DownloadLogViewer(
                log = uiState.resultLog,
                modifier = Modifier.fillMaxWidth(),
                onCopyLog = viewModel::copyLogToClipboard,
                onClearLog = viewModel::clearLog
            )

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


