package freeapp.me.yt_dlp_gui.presentation.queue

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import freeapp.me.yt_dlp_gui.domain.model.DownloadStatus
import freeapp.me.yt_dlp_gui.domain.model.QueueItem

data class QueueUiState(
    val currentQueue: QueueItem = QueueItem(),
    val queueItems: List<QueueItem> = emptyList(),
)


data class DownloadItem(
    val id: String = java.util.UUID.randomUUID().toString(), // 고유 ID (목록 key로 사용)
    val name: String, // 파일 이름 (예: test2.mp4.part)
    val type: String = "알 수 없음", // 파일 유형 (예: 알 수 없음)
    val size: String, // 파일 크기 (예: 147.31 MB)
    val status: DownloadStatus, // 다운로드 상태 (예: 대기, 다운로드 중, 완료, 실패)
    val isSelected: MutableState<Boolean> = mutableStateOf(false) // 체크박스 상태
)
