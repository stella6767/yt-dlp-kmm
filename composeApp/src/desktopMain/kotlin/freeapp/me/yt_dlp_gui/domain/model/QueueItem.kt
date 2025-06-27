package freeapp.me.yt_dlp_gui.domain.model

import java.util.UUID

data class QueueItem(
    val id: String = UUID.randomUUID().toString(),
    val thumbnail:String = "",
    val duration: Double = 0.00,
    val title: String = "",
    val url: String = "",
    val fileName: String = "",
    val additionalArguments: String = "",
    val format: String = "",
    val downloadType: DownloadType = DownloadType.AUDIO,
    val startTime: String = "",
    val endTime: String = "",

    val status: DownloadStatus = DownloadStatus.PENDING,
    val progress: Float = 0f,
    val speed: String = "",
    val eta: String = "", // New: Estimated time of arrival
    val currentLog: String = "",
)
