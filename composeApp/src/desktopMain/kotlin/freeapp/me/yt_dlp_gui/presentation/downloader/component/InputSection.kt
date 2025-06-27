package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextInputSection(
    value: String,
    title: String,
    placeholder: String,
    width: Dp,
    onValueChange: (String) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp) // 컴포넌트 간 기본 간격 추가
    ) {

        Text(
            "$title:",
            maxLines = 1,
            modifier = Modifier.width(width),
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true, // 단일 라인 입력 유지
            // 텍스트 스타일: 색상과 글자 크기 지정
            textStyle = TextStyle(
                color = Color.White, // <-- 입력 텍스트 색상
                fontSize = 14.sp // <-- 텍스트 크기를 40dp 높이에 맞게 조절
            ),
            decorationBox = { innerTextField ->

                androidx.compose.material.TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    placeholder = {
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                fontSize = 10.sp,
                            )
                        }
                    },
                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                        top = 0.dp,
                        bottom = 0.dp
                    )
                )
            },

            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary), // 커서 색상
            modifier = Modifier
                .weight(1f) // Box의 모든 공간을 BasicTextField가 채우도록
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
                // !!! BasicTextField 내부 패딩 조절 !!!
                .padding(horizontal = 8.dp, vertical = 5.dp) // <-- 텍스트가 잘리지 않도록 수직 패딩 조절
        )

    }
}

