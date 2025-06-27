package freeapp.me.yt_dlp_gui.domain.model.queue


data class DownloaderState(
    val url: String,
    val fileName: String,
    val additionalArguments: String,
    val downloadType: DownloadType,
    val startTime: String,
    val endTime: String,
) {


}

