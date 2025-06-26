package freeapp.me.yt_dlp_gui.presentation.setting

import androidx.compose.ui.graphics.vector.ImageVector
import freeapp.me.yt_dlp_gui.util.findYtDlpPath
import freeapp.me.yt_dlp_gui.util.getDefaultDownloadDir

data class SettingUiState(
    val saveToDirectory: String = getDefaultDownloadDir(),
    val ytDlpPath: String = findYtDlpPath()
)
