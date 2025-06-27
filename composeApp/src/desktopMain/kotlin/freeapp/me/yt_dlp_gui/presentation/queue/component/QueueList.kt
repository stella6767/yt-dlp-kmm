package freeapp.me.yt_dlp_gui.presentation.queue.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.domain.model.queue.QueueItem


@Composable
fun QueueList(
    downloadItems: List<QueueItem>,
    modifier: Modifier = Modifier,
    onItemCheckedChanged: (QueueItem, Boolean) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium, // 모서리 둥글게
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), // 배경색
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // 그림자
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // 헤더 영역
            ListHeader(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)) // 구분선

            // 다운로드 항목 리스트
            if (downloadItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No url in queue.", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp), // 최대 높이 지정 (스크롤 가능)
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // 항목 간 간격
                ) {
                    items(items = downloadItems, key = { it.id }) { item ->
                        DownloadListItem(
                            item = item,
                            onCheckedChanged = { isChecked -> onItemCheckedChanged(item, isChecked) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 각 컬럼 헤더
        Text("status", modifier = Modifier.weight(0.1f), fontWeight = FontWeight.Bold)
        Text("title", modifier = Modifier.weight(0.3f), fontWeight = FontWeight.Bold)
        Text("Speed", modifier = Modifier.weight(0.1f), fontWeight = FontWeight.Bold)
        Text("Progress", modifier = Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
        Text("Size", modifier = Modifier.weight(0.1f), fontWeight = FontWeight.Bold)
        Text("Added on", modifier = Modifier.weight(0.1f), fontWeight = FontWeight.Bold)
        Text("Action", modifier = Modifier.weight(0.1f), fontWeight = FontWeight.Bold)

    }
}

@Composable
fun DownloadListItem(
    item: QueueItem,
    modifier: Modifier = Modifier,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small) // 항목 배경색
            .padding(vertical = 8.dp), // 항목 내부 패딩
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {


        Text(
            item.url,
            modifier = Modifier.weight(0.3f),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            item.title,
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(item.duration.toString(), modifier = Modifier.weight(0.15f), style = MaterialTheme.typography.bodyMedium)
        Text("status", modifier = Modifier.weight(0.1f), style = MaterialTheme.typography.bodyMedium)


    }
}

