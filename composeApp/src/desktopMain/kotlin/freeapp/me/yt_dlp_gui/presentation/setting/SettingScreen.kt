package freeapp.me.yt_dlp_gui.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import freeapp.me.yt_dlp_gui.presentation.downloader.component.FolderInputSection
import freeapp.me.yt_dlp_gui.presentation.setting.component.FolderInputSection2
import freeapp.me.yt_dlp_gui.presentation.setting.component.TestInputSection
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SettingScreen(
    viewModel: SettingViewModel = koinViewModel<SettingViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // 스크롤 가능하게
            .padding(16.dp)
    ) {
        // --- 1. 저장 디렉토리 섹션 ---
        SettingsSectionHeader(
            text = "Save to",
            description = "Set the default download path; changes to the path only affect new downloads."
        )
        Spacer(Modifier.height(8.dp))

        FolderInputSection2(
            value = uiState.saveToDirectory,
            "???",
            100.dp,
            viewModel::onSaveToDirectoryBrowseClick,
            Icons.Default.FolderOpen,
        )

        Spacer(Modifier.height(10.dp))
        Divider() // 섹션 구분선

        SettingsSectionHeader(text = "yt-dlp path", description = "Set your executable file path")
        Spacer(Modifier.height(8.dp))

        FolderInputSection2(
            value = uiState.ytDlpPath,
            placeholder = uiState.ytDlpPath,
            100.dp,
            viewModel::onYTDlpPathBrowseClick,
            Icons.Default.FileOpen,
        )

        Divider() // 섹션 구분선


    }

}


@Composable
fun SettingsSectionHeader(text: String, description: String? = null) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
