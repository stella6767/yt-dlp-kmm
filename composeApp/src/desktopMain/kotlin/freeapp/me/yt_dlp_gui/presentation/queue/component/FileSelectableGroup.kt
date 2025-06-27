package freeapp.me.yt_dlp_gui.presentation.queue.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.domain.model.DownloadType
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import freeapp.me.yt_dlp_gui.presentation.queue.QueueViewModel
import freeapp.me.yt_dlp_gui.util.TimeFormatTransformation
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun FileSelectableGroup(
    downloadType: DownloadType,
    onOptionSelected: (DownloadType) -> Unit
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectableGroup().fillMaxWidth()
    ) {

        DownloadType.entries.forEach { entry ->

            if (entry != DownloadType.VIDEO_PARTIAL) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = downloadType == entry,
                        onClick = { onOptionSelected(entry) }
                    )
                    Text(entry.displayName, modifier = Modifier.clickable(onClick = { onOptionSelected(entry) }))
                }
                Spacer(Modifier.height(4.dp))

            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = downloadType == entry,
                        onClick = { onOptionSelected(entry) }
                    )
                    Text(entry.displayName, modifier = Modifier.clickable(onClick = { onOptionSelected(entry) }))
                    Spacer(Modifier.width(10.dp))
                    PartialDownloadTimeSection(downloadType == entry)
                }

            }
        }
    }
}


@Composable
fun PartialDownloadTimeSection(
    isEnable: Boolean
) {

    val viewModel: QueueViewModel = koinViewModel<QueueViewModel>()
    val uiState by viewModel.uiState.collectAsState()



    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        TimeInputField(
            value = uiState.currentQueue.startTime,
            onValueChange = viewModel::updateStartTime,
            label = "HH:MM:SS",
            modifier = Modifier.width(200.dp),
            isEnable
        )
        TimeInputField(
            value = uiState.currentQueue.endTime,
            onValueChange = viewModel::updateEndTime,
            label = "HH:MM:SS",
            modifier = Modifier.width(200.dp),
            isEnable
        )
    }

}

@Composable
fun TimeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isEnable: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // 시간 형식 유효성 검사 (HH:MM:SS)
            if (newValue.isEmpty() || newValue.matches(Regex("^\\d{0,2}(:?\\d{0,2})?(:?\\d{0,2})?$"))) {
                onValueChange(newValue)
            }
        },
        enabled = isEnable,
        label = { Text(label) },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = TimeFormatTransformation()
    )

}

