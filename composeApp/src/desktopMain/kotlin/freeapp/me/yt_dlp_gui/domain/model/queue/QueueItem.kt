package freeapp.me.yt_dlp_gui.domain.model.queue

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*

data class QueueItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val url: String = "",
    val size: String = "",
    val fileName: String = "",
    val additionalArguments: String = "",
    val format: String = "",
    val downloadType: DownloadType = DownloadType.AUDIO,
    val addOnTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),

    val startTime: String = "",
    val endTime: String = "",

    val status: DownloadStatus = DownloadStatus.PENDING,
    val progress: Float = 0f,
    val speed: String = "",
    val eta: String = "", // New: Estimated time of arrival
    val currentLog: String = "",
)
