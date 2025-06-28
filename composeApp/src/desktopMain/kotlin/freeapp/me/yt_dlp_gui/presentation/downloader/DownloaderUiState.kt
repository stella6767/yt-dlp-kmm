package freeapp.me.yt_dlp_gui.presentation.downloader

import freeapp.me.yt_dlp_gui.domain.model.queue.DownloadType
import freeapp.me.yt_dlp_gui.domain.model.queue.DownloaderState
import freeapp.me.yt_dlp_gui.domain.util.formatTimeString

data class DownloaderUiState(
    val url: String = "",
    val fileName: String = "",
    val additionalArguments: String = "",
    val resultLog: String = "",
    val isDownloading: Boolean = false,
    val downloadType: DownloadType = DownloadType.AUDIO,
    val startTime: String = "",
    val endTime: String = "",
) {

    fun toDomain(): DownloaderState {
        return DownloaderState(
            url = url,
            fileName = fileName,
            additionalArguments = additionalArguments,
            downloadType = downloadType,
            formatTimeString(startTime) ,
            formatTimeString(endTime)
        )
    }

}
