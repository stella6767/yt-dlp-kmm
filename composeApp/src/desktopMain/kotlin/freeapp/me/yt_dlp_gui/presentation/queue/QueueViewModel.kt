package freeapp.me.yt_dlp_gui.presentation.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.domain.model.DownloadType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class QueueViewModel(
    private val ytDlpService: YTDlpService
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(QueueUiState())

    val uiState: StateFlow<QueueUiState> = _uiState.onStart {

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _uiState.value
    )


    fun addQueueItem() {

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val metadata =
                ytDlpService.extractTitle(_uiState.value.currentQueue.url)

            val newItem =
                _uiState.value.currentQueue.copy(
                    id = UUID.randomUUID().toString(),
                    title = metadata?.title ?: "", thumbnail = metadata?.thumbnail ?: "")

            _uiState.update { state ->
                state.copy(isLoading = false, queueItems = listOf(newItem) + state.queueItems )
            }
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


}
