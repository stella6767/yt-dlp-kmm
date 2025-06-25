package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun DownloadLogViewer(
    log: String,
    modifier: Modifier = Modifier,
    onCopyLog: () -> Unit,
    onClearLog: () -> Unit
) {
    val logLines = remember(log) { log.split("\n") }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 로그가 업데이트되면 자동으로 아래로 스크롤
    LaunchedEffect(logLines.size) {
        if (logLines.isNotEmpty()) {
            scrollState.animateScrollToItem(logLines.size - 1)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp)
    ) {
        // 로그 내용 표시
        if (logLines.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Download log will appear here...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            SelectionContainer {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(logLines) { line ->
                        LogLineItem(line = line)
                    }
                }
            }
        }

        // 로그 제어 버튼 (우측 하단)
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "맨 위로 스크롤"
                )
            }

            Spacer(Modifier.width(8.dp))

            IconButton(
                onClick = onCopyLog,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "로그 복사"
                )
            }

            Spacer(Modifier.width(8.dp))

            IconButton(
                onClick = onClearLog,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "로그 지우기"
                )
            }
        }
    }
}


@Composable
fun LogLineItem(line: String) {
    val annotatedText = buildAnnotatedString {
        when {
            line.contains("[download]", ignoreCase = true) -> {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("[download] ")
                }
                append(line.substringAfter("[download] "))
            }
            line.contains("error", ignoreCase = true) -> {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
                    append(line)
                }
            }
            line.contains("warning", ignoreCase = true) -> {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
                    append(line)
                }
            }
            line.contains("complete", ignoreCase = true) -> {
                withStyle(SpanStyle(color = Color(0xFF4CAF50))) {
                    append(line)
                }
            }
            else -> {
                append(line)
            }
        }
    }

    Text(
        text = annotatedText,
        fontFamily = FontFamily.Monospace,
        fontSize = 12.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    )
}
