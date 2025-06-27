package freeapp.me.yt_dlp_gui.presentation.queue

import freeapp.me.yt_dlp_gui.domain.model.DataError
import freeapp.me.yt_dlp_gui.domain.model.queue.QueueItem

data class QueueUiState(
    val currentQueue: QueueItem = QueueItem(),
    val queueItems: List<QueueItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null,
)



