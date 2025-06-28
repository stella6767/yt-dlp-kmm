package freeapp.me.yt_dlp_gui.presentation.queue

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
import freeapp.me.yt_dlp_gui.presentation.global.component.FileSelectableGroup
import freeapp.me.yt_dlp_gui.presentation.queue.component.InputSectionContainer
import freeapp.me.yt_dlp_gui.presentation.global.component.ErrorDialog
import freeapp.me.yt_dlp_gui.presentation.queue.component.QueueList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun QueueScreen(
    viewModel: QueueViewModel = koinViewModel<QueueViewModel>()
) {

    val uiState by viewModel.uiState.collectAsState()
    val stateVertical = rememberScrollState(0)


    if (uiState.error != null) {
        ErrorDialog(
            viewModel::updateError,
            Modifier,
            uiState.error!!
        )
    }

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

            InputSectionContainer(viewModel)

            FileSelectableGroup(
                uiState.currentQueue.downloadType,
                viewModel::updateDownloadType,
                uiState.currentQueue.startTime,
                uiState.currentQueue.endTime,
                viewModel::updateStartTime,
                viewModel::updateEndTime,
            )

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = viewModel::addQueueItem,
                    enabled = !uiState.isLoading,
                ) {
                    Text("Add")
                }
                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = viewModel::clearQueues,
                    enabled = uiState.queueItems.isNotEmpty(),
                ) {
                    Text("Clear")
                }
                Spacer(Modifier.width(16.dp))

            }



            QueueList(
                downloadItems = uiState.queueItems,
//                onItemCheckedChanged = { item, isChecked ->
//                    viewModel.updateDownloadItemSelection(item, isChecked)
//                },
                onItemCheckedChanged = { item, isChecked -> },
                modifier = Modifier.fillMaxWidth()
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
