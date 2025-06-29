package freeapp.me.yt_dlp_gui.presentation.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kanro.compose.jetbrains.expui.control.Label


@Composable
fun FolderInputSection2(
    value: String,
    placeholder: String,
    width: Dp,
    function: () -> Unit,
    imageVector: ImageVector,
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp) // 컴포넌트 간 기본 간격 추가
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(4.dp)
                )
                .clickable { function() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // 텍스트 필드 대신 읽기 전용 텍스트 사용
            if (value.isNotEmpty()) {
                Label(
                    text = value,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Label(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


        Spacer(Modifier.width(8.dp))

        Button(
            onClick = { function() },
            modifier = Modifier.height(56.dp), // 텍스트 필드와 높이 맞추기
            shape = RoundedCornerShape(8.dp), // 둥근 모서리
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)) // 버튼 색상 (회색)
        ) {
            Icon(imageVector = imageVector, contentDescription = "Browse", tint = Color.Black)
        }

    }
}
