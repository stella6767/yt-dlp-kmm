package freeapp.me.yt_dlp_gui.presentation.layout

import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MTopAppBar(onToggleSidebar: () -> Unit) {

    TopAppBar(
        title = {
            Text(
                text = "yt-dlp GUI", // TopAppBar 타이틀
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, // 타이틀 가운데 정렬
                style = MaterialTheme.typography.titleSmall
            )
        },
        navigationIcon = {
            // TopAppBar의 navigationIcon 슬롯에 사이드바 토글 버튼 배치
            IconButton(onClick = onToggleSidebar) {
                Icon(Icons.Filled.Menu, contentDescription = "Toggle Sidebar")
            }
        },
    )
}
