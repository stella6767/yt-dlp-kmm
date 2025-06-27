package freeapp.me.yt_dlp_gui.domain.model

import java.util.UUID

data class QueueItem(
    val id: String = UUID.randomUUID().toString(),
    val thumbnail:String = "",
    val title: String = "",
    val url: String = "",
    val fileName: String = "",
    val additionalArguments: String = "",
    val format: String = "",
    val downloadType: DownloadType = DownloadType.AUDIO,
    val startTime: String = "",
    val endTime: String = "",
)
