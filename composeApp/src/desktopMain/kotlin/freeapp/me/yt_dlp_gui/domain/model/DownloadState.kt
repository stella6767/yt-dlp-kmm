package freeapp.me.yt_dlp_gui.domain.model


data class DownloaderState(
    val url: String,
    val fileName: String,
    val additionalArguments: String,
    val downloadType: DownloadType,
    val format: String,
    val startTime: String,
    val endTime: String,
) {


}
