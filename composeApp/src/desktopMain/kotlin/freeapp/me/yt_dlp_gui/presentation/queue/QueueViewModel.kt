package freeapp.me.yt_dlp_gui.presentation.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.domain.model.onError
import freeapp.me.yt_dlp_gui.domain.model.onSuccess
import freeapp.me.yt_dlp_gui.domain.model.queue.DownloadStatus
import freeapp.me.yt_dlp_gui.domain.model.queue.DownloadType
import freeapp.me.yt_dlp_gui.domain.model.queue.QueueItem
import freeapp.me.yt_dlp_gui.domain.repository.QueueRepository
import freeapp.me.yt_dlp_gui.domain.util.parseYtdlpProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class QueueViewModel(
    private val ytDlpService: YTDlpService,
    private val queueRepository: QueueRepository,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(QueueUiState())

    val uiState: StateFlow<QueueUiState> = _uiState.onStart {
        loadInitItems()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _uiState.value
    )


    fun loadInitItems() {
        _uiState.update { state ->
            state.copy(queueItems = queueRepository.getAll())
        }
    }


    fun addQueueItem() {

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val metadata =
                ytDlpService.extractMetaData(_uiState.value.currentQueue.url)

            metadata.onSuccess { metadata ->

                val newItem =
                    _uiState.value.currentQueue.copy(
                        id = UUID.randomUUID().toString(),
                        title = metadata?.title ?: "",
                        thumbnail = metadata?.thumbnail ?: "",
                        duration = metadata?.duration ?: 0.00,
                    )

                _uiState.update { state ->
                    state.copy(
                        isLoading = false, queueItems = listOf(
                            queueRepository.add(newItem)
                        ) + state.queueItems
                    )
                }

            }.onError { error ->
                _uiState.update { state ->
                    state.copy(error = error, isLoading = false)
                }
            }
            // 항목 추가 후 즉시 다운로드 프로세스 시작 시도
            //startProcessingQueue()
        }
    }


    fun updateError() {
        _uiState.update { state ->
            state.copy(error = null)
        }
    }


    fun updateUrl(newUrl: String) {
        _uiState.update { state ->
            state.copy(currentQueue = state.currentQueue.copy(url = newUrl))
        }
    }

    fun updateFileName(newFileName: String) {
        _uiState.update { state ->
            state.copy(currentQueue = state.currentQueue.copy(fileName = newFileName))
        }
    }


    fun updateAdditionalArguments(newArgs: String) {
        _uiState.update { state ->
            state.copy(currentQueue = state.currentQueue.copy(additionalArguments = newArgs))
        }
    }

    fun updateDownloadType(newDownloadType: DownloadType) {
        _uiState.update { state ->
            state.copy(currentQueue = state.currentQueue.copy(downloadType = newDownloadType))
        }
    }


    fun updateStartTime(newStartTime: String) {
        _uiState.update { state ->
            state.copy(currentQueue = state.currentQueue.copy(startTime = newStartTime))
        }
    }

    fun updateEndTime(newEndTime: String) {
        _uiState.update { state ->
            state.copy(currentQueue = state.currentQueue.copy(endTime = newEndTime))
        }
    }

    private fun updateQueueItem(updatedItem: QueueItem) {
        _uiState.update { state ->

            val items = state.queueItems.map { item ->
                if (item.id == updatedItem.id) updatedItem else item
            }

            state.copy(queueItems = items)
        }
    }

    private fun startProcessingQueue() {

        val items = queueRepository.getAll()

        viewModelScope.launch {
            while (items.any { it.status == DownloadStatus.PENDING }) {

//                val nextItem = _uiState
//                    .value.queueItems.firstOrNull { it.status == DownloadStatus.PENDING }

                val nextItem =
                    items.firstOrNull { it.status == DownloadStatus.PENDING }

                if (nextItem == null) {
                    println("No more pending items in queue.")
                    break // 모든 pending 항목 처리 완료
                }

                println("Starting download for: ${nextItem.title}")
                updateQueueItem(nextItem.copy(status = DownloadStatus.DOWNLOADING, currentLog = "Starting download..."))

                // yt-dlpService.downloadVideo는 블로킹이므로 Dispatchers.IO에서 실행
                val (exitCode, output) = withContext(Dispatchers.IO) {
                    ytDlpService.downloadItem(
                        item = nextItem,
                        onStateUpdate = { logLine ->
                            updateQueueItem(parseYtdlpProgress(nextItem, logLine))
                        }
                    )
                }

                val finalStatus =
                    if (exitCode == 0) DownloadStatus.COMPLETED else DownloadStatus.FAILED
                val finalLog =
                    if (exitCode == 0) "Download Completed" else "Download Failed (Code: $exitCode)\n$output"
                val finalProgress =
                    if (exitCode == 0) 1f else nextItem.progress // Set to 100% on success


                updateQueueItem(
                    nextItem.copy(
                        status = finalStatus,
                        currentLog = finalLog,
                        progress = finalProgress
                    )
                )

                // 다운로드가 취소되었는지 확인하고, 취소된 경우 루프 종료
//                if (currentDownloadJob?.isCancelled == true) {
//                    println("Download queue processing cancelled.")
//                    break
//                }
            }


        }


    }


//    fun abortCurrentDownload() {
//        ytDlpService.abortDownload { logLine ->
//            println("Abort status: $logLine")
//
//            queueRepository.update()
//
//            // 현재 다운로드 중인 항목이 있다면 상태를 ABORTED로 업데이트
//            _uiState.update { state ->
//                val map = state.queueItems.map { item ->
//                    if (item.status == DownloadStatus.DOWNLOADING) {
//                        item.copy(status = DownloadStatus.ABORTED, currentLog = "Download Aborted: $logLine")
//                    } else {
//                        item
//                    }
//                }
//                state
//            }
//
//            _isDownloading.value = false // 전체 다운로드 상태를 비활성화
//        }
//    }


}
