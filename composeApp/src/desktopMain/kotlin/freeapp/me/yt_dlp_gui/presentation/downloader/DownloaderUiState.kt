package freeapp.me.yt_dlp_gui.presentation.downloader

import freeapp.me.yt_dlp_gui.domain.model.DownloadType
import freeapp.me.yt_dlp_gui.domain.model.DownloaderState

data class DownloaderUiState(
    val url: String = "",
    val fileName: String = "",
    val saveToDirectory: String = "",
    val additionalArguments: String = "",
    val ytDlpPath: String = "/opt/homebrew/Cellar/yt-dlp/2025.4.30/libexec/bin/yt-dlp", // 기본 경로
    val resultLog: String = "",
    val isDownloading: Boolean = false,
    val format: String = "",
    val downloadType: DownloadType = DownloadType.AUDIO,
    val startTime: String = "",
    val endTime: String = "",
) {

    fun toDomain(): DownloaderState {
        return DownloaderState(
            url = url,
            fileName = fileName,
            saveToDirectory = saveToDirectory,
            additionalArguments = additionalArguments,
            ytDlpPath = ytDlpPath,
            downloadType = downloadType,
            format = format,
            startTime,
            endTime
        )
    }

}
