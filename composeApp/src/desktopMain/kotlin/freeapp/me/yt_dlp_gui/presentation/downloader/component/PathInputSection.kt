package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PathInputSection(
    title: String,
    placeholder: String,
    width: Dp,
    imageVector: ImageVector? = null,
) {

    var path by remember { mutableStateOf("") } // 경로 상태 관리

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
            value = path,
            onValueChange = { path = it },
            singleLine = true, // 단일 라인 입력 유지
            // 텍스트 스타일: 색상과 글자 크기 지정
            textStyle = TextStyle(
                color = Color.White, // <-- 입력 텍스트 색상
                fontSize = 14.sp // <-- 텍스트 크기를 40dp 높이에 맞게 조절
            ),

            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary), // 커서 색상
            modifier = Modifier
                .weight(1f) // Box의 모든 공간을 BasicTextField가 채우도록
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
                // !!! BasicTextField 내부 패딩 조절 !!!
                .padding(horizontal = 8.dp, vertical = 5.dp) // <-- 텍스트가 잘리지 않도록 수직 패딩 조절
        )



        Spacer(Modifier.width(8.dp))
        if (imageVector != null) {
            Button(
                onClick = {
                    // 실제 파일 탐색기 열기 로직은 여기에 구현되지 않습니다.
                    println("$title 찾아보기 버튼 클릭됨!")
                },
                modifier = Modifier.height(56.dp), // 텍스트 필드와 높이 맞추기
                shape = RoundedCornerShape(8.dp), // 둥근 모서리
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)) // 버튼 색상 (회색)
            ) {
                Icon(imageVector = imageVector, contentDescription = "Browse", tint = Color.Black)
            }
        }


    }
}


// 동영상 URL 입력을 위한 컴포저블
@Composable
fun UrlInputSection() {
    var url by remember { mutableStateOf("") } // URL 상태 관리

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "동영상 URL", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = url,
            onValueChange = { url = it }, // 값 변경 시 상태 업데이트
            label = { Text("동영상 URL을 입력하세요 (예: https://www.youtube.com/watch?v=...)") },
            modifier = Modifier.fillMaxWidth(), // 너비 전체 채우기
            singleLine = true, // 단일 라인 입력
            shape = RoundedCornerShape(8.dp), // 둥근 모서리
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = Color.LightGray,
            )
        )
    }
}


@Composable
fun DownloadFolderSection() {
    var downloadPath by remember { mutableStateOf("") } // 다운로드 경로 상태 관리

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "다운로드 폴더", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 다운로드 폴더 입력 필드
            OutlinedTextField(
                value = downloadPath,
                onValueChange = { downloadPath = it }, // 값 변경 시 상태 업데이트
                label = { Text("다운로드 폴더 경로 (예: ~/Downloads)") },
                modifier = Modifier.weight(1f), // 남은 공간 모두 차지
                singleLine = true, // 단일 라인 입력
                shape = RoundedCornerShape(8.dp), // 둥근 모서리
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.LightGray,
                )

            )
            // 찾아보기 버튼
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    // 실제 폴더 탐색기 열기 로직은 여기에 구현되지 않습니다.
                    println("다운로드 폴더 찾아보기 버튼 클릭됨!")
                },
                modifier = Modifier.height(56.dp), // 텍스트 필드와 높이 맞추기
                shape = RoundedCornerShape(8.dp), // 둥근 모서리
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
            ) {
                Icon(Icons.Default.FolderOpen, contentDescription = "Browse Download Folder", tint = Color.Black)
            }
        }
    }
}

// yt-dlp 옵션 입력을 위한 컴포저블
@Composable
fun YtdlpOptionsSection() {
    var options by remember { mutableStateOf("") } // 옵션 상태 관리

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "yt-dlp 옵션",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = options,
            onValueChange = { options = it }, // 값 변경 시 상태 업데이트
            label = { Text("yt-dlp 추가 옵션 (예: -f bestvideo+bestaudio --merge-output-format mp4)") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp), // 여러 줄 입력을 위해 높이 설정
            maxLines = 5, // 최대 5줄까지 표시
            shape = RoundedCornerShape(8.dp), // 둥근 모서리
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = Color.LightGray,
            )
        )
        Text(
            text = "각 옵션은 공백으로 구분합니다.",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
