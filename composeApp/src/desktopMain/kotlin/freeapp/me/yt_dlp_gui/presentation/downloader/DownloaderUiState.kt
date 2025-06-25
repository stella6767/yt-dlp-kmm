package freeapp.me.yt_dlp_gui.presentation.downloader

import freeapp.me.yt_dlp_gui.domain.model.DownloadState

data class DownloaderUiState(
    val url: String = "",
    val fileName: String = "",
    val saveToDirectory: String = "",
    val additionalArguments: String = "",
    val ytdlpPath: String = "/usr/local/bin/yt-dlp", // 기본 경로
    val resultLog:String = "",
    val downloadState: DownloadState = DownloadState.Idle
)
