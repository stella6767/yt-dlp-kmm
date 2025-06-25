package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import freeapp.me.yt_dlp_gui.domain.model.DownloadType


@Composable
fun FileSelectableGroup(
    downloadType: DownloadType,
    onOptionSelected: (DownloadType) -> Unit
) {


    Row(modifier = Modifier.selectableGroup()) {


        DownloadType.entries.forEach { entry ->

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = downloadType == entry,
                    onClick = { onOptionSelected(entry) }
                )
                Text(entry.displayName, modifier = Modifier.clickable(onClick = { onOptionSelected(entry) }))
            }
            Spacer(Modifier.height(4.dp))

        }


//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = selectedVideoOption1 == "Audio",
//                onClick = { selectedVideoOption1 = "Audio" }
//            )
//            Text("Audio", modifier = Modifier.clickable(onClick = { selectedVideoOption1 = "Audio" }))
//        }
//        Spacer(Modifier.height(4.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = selectedVideoOption1 == "Video (full)",
//                onClick = { selectedVideoOption1 = "Video (full)" }
//            )
//            Text("Video (full)", modifier = Modifier.clickable(onClick = { selectedVideoOption1 = "Video (full)" }))
//        }
//        Spacer(Modifier.height(4.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = selectedVideoOption1 == "Video (partial)",
//                onClick = { selectedVideoOption1 = "Video (partial)" }
//            )
//            Text(
//                "Video (partial)",
//                modifier = Modifier.clickable(onClick = { selectedVideoOption1 = "Video (partial)" })
//            )
//            Spacer(Modifier.width(16.dp))
//            // Video (partial)일 때만 Start/End 필드 표시
//            if (selectedVideoOption1 == "Video (partial)") {
//                Text("Start:")
//                TextField(
//                    value = "", onValueChange = {},
//                    modifier = Modifier.width(80.dp).padding(horizontal = 4.dp),
//                    singleLine = true,
//                    colors = TextFieldDefaults.colors(unfocusedTextColor = Color.LightGray) // 비활성화 느낌
//                )
//                Text("End:")
//                TextField(
//                    value = "", onValueChange = {},
//                    modifier = Modifier.width(80.dp).padding(horizontal = 4.dp),
//                    singleLine = true,
//                    colors = TextFieldDefaults.colors(unfocusedTextColor = Color.LightGray) // 비활성화 느낌
//                )
//            }
//        }
    }
}
