package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.domain.model.DownloadType
import freeapp.me.yt_dlp_gui.util.FileChooser
import freeapp.me.yt_dlp_gui.util.formatTimeString

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    fun updateSaveToDirectory(newDirectory: String) {
        _uiState.update { state ->
            state.copy(saveToDirectory = newDirectory)
        }
    }

    fun updateAdditionalArguments(newArgs: String) {
        _uiState.update { state ->
            state.copy(additionalArguments = newArgs)
        }
    }

    fun updateYTDlpPath(newPath: String) {
        _uiState.update { state ->
            state.copy(ytDlpPath = newPath)
        }
    }

    fun updateDownloadType(newDownloadType: DownloadType) {
        _uiState.update { state ->
            state.copy(downloadType = newDownloadType)
        }
    }


    fun updateFormat(newFormat: String) {

        println("updateFormat: $newFormat")

        val format =
            if (newFormat == "Default") "" else newFormat

        _uiState.update { state ->
            state.copy(format = format)
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

    fun onSaveToDirectoryBrowseClick() {
        viewModelScope.launch {
            val selectedFolder = FileChooser.chooseDirectory()
            selectedFolder?.let { updateSaveToDirectory(it) }
        }
    }

    fun onYTDlpPathBrowseClick() {
        viewModelScope.launch {
            val selectedFile = FileChooser.chooseFile(
                title = "Select File",
                allowedExtensions = listOf(""), // 실행 파일은 확장자 필터링 안 함
            )
            selectedFile?.let { updateYTDlpPath(it) }
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

