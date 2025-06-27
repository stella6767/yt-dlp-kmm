package freeapp.me.yt_dlp_gui.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class YtDlpMetadata(
    val title: String = "",
    val webUrl: String? = null, // 원본 URL (선택 사항)
    val thumbnail: String? = null,
    val duration: Double? = null,
    val uploader: String? = null
)
