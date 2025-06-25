package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import freeapp.me.yt_dlp_gui.util.FileChooser.chooseDirectory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


@Composable
fun FolderInputSection(
    value: String,
    title: String,
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

        Text(
            "$title:",
            maxLines = 1,
            modifier = Modifier.width(width),
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )

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
                Text(
                    text = value,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
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
            onClick = {

                function()

//                coroutineScope.launch {
//                    val selectedPath = chooseDirectory()
//                    selectedPath?.let { onValueChange(it) }
//                }

                //isFileChooserOpen = true
                //openFileDialog(title = "directory choose", allowedExtensions = listOf())
            },
            modifier = Modifier.height(56.dp), // 텍스트 필드와 높이 맞추기
            shape = RoundedCornerShape(8.dp), // 둥근 모서리
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)) // 버튼 색상 (회색)
        ) {
            Icon(imageVector = imageVector, contentDescription = "Browse", tint = Color.Black)
        }

    }
}
