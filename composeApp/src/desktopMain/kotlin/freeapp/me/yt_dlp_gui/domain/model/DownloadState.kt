package freeapp.me.yt_dlp_gui.domain.model

sealed class DownloadState {
    object Idle : DownloadState()
    data class Downloading(val progress: String, val eta: String) : DownloadState()
    data class Processing(val message: String) : DownloadState()
    data class Success(val message: String) : DownloadState()
    data class Error(val message: String) : DownloadState()
    object Aborted : DownloadState()
}
