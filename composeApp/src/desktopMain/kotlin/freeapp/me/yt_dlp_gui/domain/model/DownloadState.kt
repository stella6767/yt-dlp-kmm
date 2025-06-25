package freeapp.me.yt_dlp_gui.domain.model


data class DownloaderState(
    val url: String,
    val fileName: String,
    val saveToDirectory: String,
    val additionalArguments: String,
    val ytDlpPath: String,
    val downloadType: DownloadType,
    val format: String,
    val startTime: String,
    val endTime: String,
    ) {


}
