package freeapp.me.yt_dlp_gui.presentation.queue.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.global.component.TextInputSection
import freeapp.me.yt_dlp_gui.presentation.queue.QueueViewModel


@Composable
fun InputSectionContainer(
    viewModel: QueueViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
    ) {

        TextInputSection(value = uiState.currentQueue.url,
            title = "URL",
            "",
            viewModel::updateUrl
        )
        TextInputSection(
            value = uiState.currentQueue.fileName,
            title = "File name",
            "leave empty for default name",
            viewModel::updateFileName
        )

        // Additional arguments
        TextInputSection(
            value = uiState.currentQueue.additionalArguments,
            title = "Additional arguments",
            placeholder = "",
            viewModel::updateAdditionalArguments
        )


    }
}

