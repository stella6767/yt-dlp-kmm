package freeapp.me.yt_dlp_gui.presentation.queue

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import freeapp.me.yt_dlp_gui.domain.model.DownloadStatus
import freeapp.me.yt_dlp_gui.domain.model.QueueItem

data class QueueUiState(
    val currentQueue: QueueItem = QueueItem(),
    val queueItems: List<QueueItem> = emptyList(),
    val isLoading: Boolean = false,
)



