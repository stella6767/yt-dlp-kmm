package freeapp.me.yt_dlp_gui.presentation.downloader

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.util.FileChooser

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

//    // --- 다운로드 관련 비즈니스 로직 함수들 ---
//    fun startDownload() {
//        // 현재 UI 상태의 값을 사용하여 다운로드 시작
//        _uiState.value.let { state ->
//            ytDlpService.downloadVideo(
//                url = state.url,
//                fileName = state.fileName,
//                saveToDirectory = state.saveToDirectory,
//                additionalArguments = state.additionalArguments
//            )
//        }
//    }
//
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
//        viewModelScope.launch {
//            val selectedFile = FileChooser.chooseFile( // Desktop 전용 함수 호출 (expect/actual)
//                allowedExtensions = listOf(""), // 실행 파일은 확장자 필터링 안 함
//                allowMultiSelection = false
//            )
//            selectedFile?.let { updateYTDlpPath(it) }
//        }
    }




}
