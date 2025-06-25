//package freeapp.me.yt_dlp_gui.presentation.downloader.component
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import freeapp.me.yt_dlp_gui.domain.model.DownloadType
//
//@Composable
//fun DownloadOptionsSection(
//    onOptionsChanged: (DownloadType, String?, String?) -> Unit
//) {
//    var downloadType by remember { mutableStateOf(DownloadType.VIDEO_FULL) }
//    var startTime by remember { mutableStateOf("") }
//    var endTime by remember { mutableStateOf("") }
//
//    // 오디오/비디오 포맷 선택 비활성화 상태
//    val isAudioDisabled = downloadType != DownloadType.AUDIO
//    val isVideoDisabled = downloadType == DownloadType.AUDIO
//
//    // 상태 변경 시 콜백 호출
//    LaunchedEffect(downloadType,  startTime, endTime) {
//        onOptionsChanged(
//            downloadType,
////            if (downloadType == DownloadType.AUDIO) audioFormat else null,
////            if (downloadType != DownloadType.AUDIO) videoFormat else null,
//            if (downloadType == DownloadType.VIDEO_PARTIAL) startTime else null,
//            if (downloadType == DownloadType.VIDEO_PARTIAL) endTime else null
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        // 다운로드 유형 선택
//        Text("다운로드 유형", style = MaterialTheme.typography.titleMedium)
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            DownloadTypeOption(
//                type = DownloadType.AUDIO,
//                icon = Icons.Default.MusicNote,
//                label = "오디오",
//                selected = downloadType == DownloadType.AUDIO,
//                onClick = { downloadType = DownloadType.AUDIO }
//            )
//
//            DownloadTypeOption(
//                type = DownloadType.VIDEO_FULL,
//                icon = Icons.Default.VideoLibrary,
//                label = "전체 비디오",
//                selected = downloadType == DownloadType.VIDEO_FULL,
//                onClick = { downloadType = DownloadType.VIDEO_FULL }
//            )
//
//            DownloadTypeOption(
//                type = DownloadType.VIDEO_PARTIAL,
//                icon = Icons.Default.Timelapse,
//                label = "부분 비디오",
//                selected = downloadType == DownloadType.VIDEO_PARTIAL,
//                onClick = { downloadType = DownloadType.VIDEO_PARTIAL }
//            )
//        }
//
//        // 포맷 선택 섹션
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(24.dp)
//        ) {
//            // 오디오 포맷 선택
//            Column(
//                modifier = Modifier.weight(1f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("오디오 포맷", style = MaterialTheme.typography.titleSmall)
//
//                AudioFormat.values().forEach { format ->
//                    FormatOption(
//                        label = format.name,
//                        selected = audioFormat == format,
//                        onClick = { audioFormat = format },
//                        enabled = !isAudioDisabled
//                    )
//                }
//            }
//
//            // 비디오 포맷 선택
//            Column(
//                modifier = Modifier.weight(1f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("비디오 포맷", style = MaterialTheme.typography.titleSmall)
//
//                VideoFormat.values().forEach { format ->
//                    FormatOption(
//                        label = format.name,
//                        selected = videoFormat == format,
//                        onClick = { videoFormat = format },
//                        enabled = !isVideoDisabled
//                    )
//                }
//            }
//        }
//
//        // 부분 다운로드 시간 설정 (부분 비디오 선택 시에만 표시)
//        if (downloadType == DownloadType.VIDEO_PARTIAL) {
//            PartialDownloadTimeSection(
//                startTime = startTime,
//                endTime = endTime,
//                onStartTimeChange = { startTime = it },
//                onEndTimeChange = { endTime = it }
//            )
//        }
//    }
//}
//
//@Composable
//fun DownloadTypeOption(
//    type: DownloadType,
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    label: String,
//    selected: Boolean,
//    onClick: () -> Unit
//) {
//    val containerColor = if (selected) {
//        MaterialTheme.colorScheme.primaryContainer
//    } else {
//        MaterialTheme.colorScheme.surfaceVariant
//    }
//
//    val contentColor = if (selected) {
//        MaterialTheme.colorScheme.onPrimaryContainer
//    } else {
//        MaterialTheme.colorScheme.onSurfaceVariant
//    }
//
//    OutlinedButton(
//        onClick = onClick,
//        modifier = Modifier.width(120.dp),
//        colors = ButtonDefaults.outlinedButtonColors(
//            containerColor = containerColor,
//            contentColor = contentColor
//        ),
//        border = if (selected) {
//            ButtonDefaults.outlinedButtonBorder.copy(
//                brush = SolidColor(MaterialTheme.colorScheme.primary)
//            )
//        } else {
//            null
//        }
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Icon(
//                imageVector = icon,
//                contentDescription = label,
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(Modifier.height(4.dp))
//            Text(label)
//        }
//    }
//}
//
//@Composable
//fun FormatOption(
//    label: String,
//    selected: Boolean,
//    onClick: () -> Unit,
//    enabled: Boolean = true
//) {
//    val textColor = if (enabled) {
//        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
//    } else {
//        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//            .clickable(enabled = enabled, onClick = onClick),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        RadioButton(
//            selected = selected,
//            onClick = null, // 상위 Row에서 처리
//            enabled = enabled
//        )
//        Text(
//            text = label,
//            color = textColor,
//            modifier = Modifier.padding(start = 8.dp)
//        )
//    }
//}
//
//@Composable
//fun PartialDownloadTimeSection(
//    startTime: String,
//    endTime: String,
//    onStartTimeChange: (String) -> Unit,
//    onEndTimeChange: (String) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Text("부분 다운로드 시간 설정", style = MaterialTheme.typography.titleMedium)
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            TimeInputField(
//                value = startTime,
//                onValueChange = onStartTimeChange,
//                label = "시작 시간 (HH:MM:SS)",
//                modifier = Modifier.weight(1f)
//
//                        TimeInputField (
//                        value = endTime,
//                onValueChange = onEndTimeChange,
//                label = "종료 시간 (HH:MM:SS)",
//                modifier = Modifier.weight(1f)
//        }
//
//        Text(
//            "※ 시간 형식: 시간:분:초 (예: 00:10:30)",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}
//
//@Composable
//fun TimeInputField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    modifier: Modifier = Modifier
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = { newValue ->
//            // 시간 형식 유효성 검사 (HH:MM:SS)
//            if (newValue.isEmpty() || newValue.matches(Regex("^\\d{0,2}(:?\\d{0,2})?(:?\\d{0,2})?$"))) {
//                onValueChange(newValue)
//            }
//        },
//        label = { Text(label) },
//        modifier = modifier,
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        visualTransformation = TimeFormatTransformation()
//    )
//}
//
//class TimeFormatTransformation : VisualTransformation {
//    override fun filter(text: AnnotatedString): TransformedText {
//        val original = text.text
//        val formatted = buildString {
//            for (i in original.indices) {
//                append(original[i])
//                if (i == 1 || i == 3) {
//                    append(':')
//                }
//            }
//        }
//
//        return TransformedText(AnnotatedString(formatted), TimeOffsetMapping)
//    }
//
//    private object TimeOffsetMapping : OffsetMapping {
//        override fun originalToTransformed(offset: Int): Int {
//            return when {
//                offset <= 1 -> offset
//                offset <= 3 -> offset + 1
//                else -> offset + 2
//            }
//        }
//
//        override fun transformedToOriginal(offset: Int): Int {
//            return when {
//                offset <= 2 -> offset
//                offset <= 5 -> offset - 1
//                else -> offset - 2
//            }
//        }
//    }
//}
