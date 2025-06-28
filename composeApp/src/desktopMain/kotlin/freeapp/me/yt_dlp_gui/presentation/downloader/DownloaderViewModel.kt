package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.domain.model.queue.DownloadType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class DownloaderViewModel(
    private val ytDlpService: YTDlpService
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(DownloaderUiState())

    val uiState: StateFlow<DownloaderUiState> = _uiState.onStart {}.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _uiState.value
    )

    // --- UI 입력 필드 업데이트 함수들 ---
    fun updateUrl(newUrl: String) {
        _uiState.update { state ->
            state.copy(url = newUrl)
        }
    }

    fun updateFileName(newFileName: String) {
        _uiState.update { state ->
            state.copy(fileName = newFileName)
        }
    }


    fun updateAdditionalArguments(newArgs: String) {
        _uiState.update { state ->
            state.copy(additionalArguments = newArgs)
        }
    }

    fun updateDownloadType(newDownloadType: DownloadType) {
        _uiState.update { state ->
            state.copy(downloadType = newDownloadType)
        }
    }



    fun updateStartTime(newStartTime: String) {

        _uiState.update { state ->
            state.copy(startTime = newStartTime)
        }
    }


    fun updateEndTime(newEndTime: String) {

        _uiState.update { state ->
            state.copy(endTime = newEndTime)
        }
    }


    // --- 다운로드 관련 비즈니스 로직 함수들 ---
    fun startDownload() {
        viewModelScope.launch {

            _uiState.update { state ->
                state.copy(isDownloading = true)
            }

            val (code, log) = ytDlpService.downloadVideo(
                scope = this,
                downloadState = _uiState.value.toDomain(),
                onStateUpdate = { state ->
                    val logLine = state + "\n"
                    _uiState.update { currentState ->
                        currentState.copy(resultLog = currentState.resultLog + logLine)
                    }
                }
            )

            println(log)

            _uiState.update { state ->
                state.copy(isDownloading = false)
            }

        }
    }

    fun abortDownload() {
        viewModelScope.launch {
            ytDlpService.abortDownload(
                onStateUpdate = { state ->
                    val logLine = state + "\n"
                    _uiState.update { currentState ->
                        currentState.copy(resultLog = currentState.resultLog + logLine)
                    }
                }
            )
        }
    }


    fun clearLog() {
        _uiState.update {
            it.copy(resultLog = "")
        }
    }

    fun copyLogToClipboard() {
        val clipboard =
            Toolkit.getDefaultToolkit().systemClipboard
        val stringSelection = StringSelection(uiState.value.resultLog)
        clipboard.setContents(stringSelection, null)
    }


}

