package freeapp.me.yt_dlp_gui.presentation.global.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kanro.compose.jetbrains.expui.control.Label
import io.kanro.compose.jetbrains.expui.control.TextField


@Composable
fun TextInputSection(
    value: String,
    title: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp) // 컴포넌트 간 기본 간격 추가
    ) {


        Label("$title:")


        TextField(
            value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 14.sp // <-- 텍스트 크기를 40dp 높이에 맞게 조절
            ),
            placeholder = {Label(placeholder, color = Color.LightGray)}
        )


    }
}

