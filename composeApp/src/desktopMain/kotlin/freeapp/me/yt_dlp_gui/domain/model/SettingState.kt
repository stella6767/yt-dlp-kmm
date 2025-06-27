package freeapp.me.yt_dlp_gui.domain.model

data class SettingState(
    var saveToDirectory: String = "",
    var ytDlpPath: String = "",
    var audioFormat: AudioFormat = AudioFormat.Default,
    var videoFormat: VideoFormat = VideoFormat.Default,
)
