package freeapp.me.yt_dlp_gui.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import freeapp.me.yt_dlp_gui.domain.model.queue.AudioFormat
import freeapp.me.yt_dlp_gui.domain.model.queue.SettingState
import freeapp.me.yt_dlp_gui.domain.model.queue.VideoFormat
import freeapp.me.yt_dlp_gui.domain.repository.SettingRepository
import freeapp.me.yt_dlp_gui.presentation.setting.component.FileChooser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingViewModel(
    private val settingRepository: SettingRepository,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(SettingUiState(SettingState()))

    val uiState: StateFlow<SettingUiState> = _uiState.onStart {
        loadSettingState()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _uiState.value
    )

    private fun loadSettingState() {
        viewModelScope.launch {
            val settingState =
                settingRepository.findSettingState()
            _uiState.update {
                it.copy(
                    settingState = settingState
                )
            }
        }
    }

    fun updateAudioFormat(format: AudioFormat) {

        val newState =
            _uiState.value.settingState.copy(audioFormat = format)

        _uiState.update { state ->
            state.copy(settingState = settingRepository.updateSettingState(newState))
        }
    }


    fun updateVideoFormat(format: VideoFormat) {
        val newState =
            _uiState.value.settingState.copy(videoFormat = format)
        _uiState.update { state ->
            state.copy(settingState = settingRepository.updateSettingState(newState))
        }

    }

    fun updateSaveToDirectory(newDirectory: String) {

        val newState =
            _uiState.value.settingState.copy(saveToDirectory = newDirectory)

        _uiState.update { state ->
            state.copy(settingState = settingRepository.updateSettingState(newState))
        }
    }

    fun updateYTDlpPath(newPath: String) {

        val newState =
            _uiState.value.settingState.copy(ytDlpPath = newPath)

        _uiState.update { state ->
            state.copy(settingState = settingRepository.updateSettingState(newState))
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

    fun updateTheme() {

        println("???")

        _uiState.update { state ->
            state.copy(settingState = state.settingState.copy(isDarkTheme = !state.settingState.isDarkTheme))
        }
    }

}
