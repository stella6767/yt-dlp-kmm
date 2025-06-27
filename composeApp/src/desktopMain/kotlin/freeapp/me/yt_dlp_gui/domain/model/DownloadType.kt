package freeapp.me.yt_dlp_gui.domain.model

enum class DownloadType(
    val displayName: String,
) {
    AUDIO("Audio"),
    VIDEO_FULL("Video Full"),
    VIDEO_PARTIAL("Video (Partial)"),
}

enum class AudioFormat { Default, MP3 }
enum class VideoFormat { Default, MP4 }


enum class DownloadStatus(

) {
    WAITING,
    DOWNLOADING,
    COMPLETED,
    FAILED,
    CANCELED
}

