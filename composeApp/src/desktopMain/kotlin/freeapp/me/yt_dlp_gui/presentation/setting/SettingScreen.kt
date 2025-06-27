package freeapp.me.yt_dlp_gui.presentation.setting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.domain.model.queue.AudioFormat
import freeapp.me.yt_dlp_gui.domain.model.queue.VideoFormat
import freeapp.me.yt_dlp_gui.presentation.setting.component.FolderInputSection2
import freeapp.me.yt_dlp_gui.presentation.setting.component.SettingDropdownRow
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
            value = uiState.settingState.saveToDirectory,
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
            value = uiState.settingState.ytDlpPath,
            placeholder = uiState.settingState.ytDlpPath,
            100.dp,
            viewModel::onYTDlpPathBrowseClick,
            Icons.Default.FileOpen,
        )

        Divider() // 섹션 구분선

        Spacer(Modifier.height(10.dp))
        SettingsSectionHeader(text = "Media Format", description = "Set the default audio and video formats.")
        Spacer(Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            SettingDropdownRow(
                label = "audio format: ",
                selectedValue = uiState.settingState.audioFormat.name, // 확장 함수 사용
                options = AudioFormat.entries.map { it.name },
                onOptionSelected = { selectedName ->
                    viewModel.updateAudioFormat(
                        AudioFormat.entries.first { it.name == selectedName }
                    )
                }
            )
            Spacer(Modifier.width(12.dp))

            SettingDropdownRow(
                label = "video format: ",
                selectedValue = uiState.settingState.videoFormat.name, // 확장 함수 사용
                options = VideoFormat.entries.map { it.name },
                onOptionSelected = { selectedName ->
                    viewModel.updateVideoFormat(
                        VideoFormat.entries.first { it.name == selectedName }
                    )
                }
            )

        }


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
