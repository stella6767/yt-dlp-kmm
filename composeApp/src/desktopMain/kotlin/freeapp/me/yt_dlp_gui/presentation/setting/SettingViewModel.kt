package freeapp.me.yt_dlp_gui.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderUiState
import freeapp.me.yt_dlp_gui.util.FileChooser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.compareTo

class SettingViewModel(
    private val ytDlpService: YTDlpService
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(SettingUiState())

    val uiState: StateFlow<SettingUiState> = _uiState.onStart {
        loadSettingState()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _uiState.value
    )

    private fun loadSettingState() {
        viewModelScope.launch {
            val (ytDlpPath, downloadPath) =
                ytDlpService.getSettingState()
            _uiState.update {
                it.copy(
                    ytDlpPath = ytDlpPath,
                    saveToDirectory = downloadPath
                )
            }
        }
    }


    fun updateSaveToDirectory(newDirectory: String) {
        val directory =
            ytDlpService.updateSaveToDirectory(newDirectory)
        _uiState.update { state ->
            state.copy(saveToDirectory = directory)
        }
    }

    fun updateYTDlpPath(newPath: String) {
        val updateYtDlpPath =
            ytDlpService.updateYtDlpPath(newPath)
        _uiState.update { state ->
            state.copy(ytDlpPath = updateYtDlpPath)
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

}
