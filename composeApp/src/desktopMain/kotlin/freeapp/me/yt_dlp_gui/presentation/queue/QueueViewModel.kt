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
import freeapp.me.yt_dlp_gui.domain.util.getErrorMessage
import freeapp.me.yt_dlp_gui.domain.util.parseYtdlpProgress
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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

            _uiState.update { it.copy(isLoading = true) }

            val metadata =
                ytDlpService.extractMetaData(_uiState.value.currentQueue.url)

            metadata.onSuccess { metadata ->
                val newItem =
                    _uiState.value.currentQueue.copy(
                        id = UUID.randomUUID().toString(),
                        title = metadata?.title ?: "",
                        size = metadata?.size ?: "",
                        addOnTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    )

                _uiState.update { state ->
                    state.copy(
                        queueItems = listOf(
                            queueRepository.add(newItem)
                        ) + state.queueItems
                    )
                }

                _uiState.update { state ->
                    state.copy(isLoading = false)
                }

                // 항목 추가 후 즉시 다운로드 프로세스 시작 시도
                startProcessingQueue()

            }.onError { error ->
                _uiState.update { state ->
                    state.copy(error = error, isLoading = false)
                }
            }

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

    private fun updateQueueItemUiState(updatedItem: QueueItem) {
        _uiState.update { state ->
            val items = state.queueItems.map { item ->
                if (item.id == updatedItem.id) updatedItem else item
            }
            state.copy(queueItems = items)
        }
    }

    private suspend fun startProcessingQueue() {

        while (true) {
            val items = queueRepository.getAll()
            val nextItem =
                items.firstOrNull { it.status == DownloadStatus.PENDING }
            println("pending Item: ${nextItem}")
            if (nextItem == null) {
                println("No more pending items in queue.")
                break // 모든 pending 항목 처리 완료
            }

            println("Starting download for: ${nextItem.title}")

            val queueItem = nextItem.copy(
                status = DownloadStatus.DOWNLOADING,
                currentLog = "Starting download..."
            )
            queueRepository.update(queueItem)
            updateQueueItemUiState(queueItem)

            ytDlpService.downloadItem(
                item = nextItem,
                onStateUpdate = { logLine ->
                    val currentItem =
                        _uiState.value.queueItems.firstOrNull { it.id == nextItem.id } ?: nextItem
                    updateQueueItemUiState(parseYtdlpProgress(currentItem, logLine))
                }
            ).onSuccess { exitCode ->

                val finalStatus =
                    if (exitCode == 0) DownloadStatus.COMPLETED else DownloadStatus.FAILED
                val finalLog =
                    if (exitCode == 0) "Download Completed" else "Download Failed (Code: $exitCode)\n "
                //$output
                val finalProgress =
                    if (exitCode == 0) 1f else nextItem.progress // Set to 100% on success

                val currentItem =
                    _uiState.value.queueItems.firstOrNull { it.id == nextItem.id } ?: nextItem

                val item = currentItem.copy(
                    status = finalStatus,
                    currentLog = finalLog,
                    progress = finalProgress,
                    speed = currentItem.speed,
                )
                println("Final!!")

                queueRepository.update(item)
                updateQueueItemUiState(item)

            }.onError { error ->
                updateQueueItemUiState(
                    nextItem.copy(
                        status = DownloadStatus.FAILED,
                        currentLog = getErrorMessage(error)
                    )
                )
                _uiState.update { state ->
                    state.copy(error = error, isLoading = false)
                }
            }
        }

    }



    fun clearQueues(){
        queueRepository.clear()
        _uiState.update { state ->
            state.copy(queueItems = emptyList())
        }
    }


    fun abortCurrentDownload() {

        ytDlpService.abortDownload { logLine ->
            println("Abort status: $logLine")
        }

        // 현재 다운로드 중인 항목이 있다면 상태를 ABORTED로 업데이트
        val map = _uiState.value.queueItems.map { item ->
            if (item.status == DownloadStatus.DOWNLOADING) {
                val abortItem = item.copy(status = DownloadStatus.ABORTED, currentLog = "Download Aborted")
                queueRepository.update(abortItem)
                abortItem
            } else {
                item
            }
        }
        _uiState.update { state ->
            state.copy(queueItems = map)
        }



    }

    fun removeItemById(id: String) {

        queueRepository.remove(id)

        _uiState.update { state ->
            val items = state.queueItems.filter { item ->
                item.id != id
            }
            state.copy(queueItems = items)
        }
    }


}
