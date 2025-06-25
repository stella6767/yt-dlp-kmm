package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.util.FileChooser

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            state.copy(ytdlpPath = newPath)
        }
    }

    // --- 다운로드 관련 비즈니스 로직 함수들 ---
    fun startDownload() {
        viewModelScope.launch {
            val (code, log) = ytDlpService.downloadVideo(
                scope = this,
                url = _uiState.value.url,
                fileName = _uiState.value.fileName,
                saveToDirectory = _uiState.value.saveToDirectory,
                additionalArguments = _uiState.value.additionalArguments,
                ytDlpPath = _uiState.value.ytdlpPath,
                onStateUpdate = {}
            )

            println(log)

            _uiState.update { state ->
                state.copy(resultLog = log)
            }
        }
    }

//    fun abortDownload() {
//        ytDlpService.abortDownload()
//    }

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
        // 실제로는 클립보드에 복사하는 로직 구현
        println("로그 복사: ${uiState.value.resultLog}")
    }


}
