package freeapp.me.yt_dlp_gui.presentation.queue.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun ThumbnailImage(thumbnailUrl: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(40.dp),
        contentAlignment = Alignment.Center
    ) {
        if (thumbnailUrl.isNullOrEmpty()) {
            Text("No Image") // 썸네일 URL이 없을 때
        } else {
            AsyncImage(
                model = thumbnailUrl, // 로드할 이미지 URL
                contentDescription = "Thumbnail", // 접근성을 위한 설명
                contentScale = ContentScale.Crop, // 이미지 크롭하여 뷰에 맞춤
                //modifier = Modifier.size(100.dp, 40.dp),
                //modifier = Modifier.clip(CircleShape),
                modifier = Modifier.fillMaxSize().height(40.dp)
                // Coil의 상태별 컴포저블:
                //onLoading =  { CircularProgressIndicator(modifier = Modifier.size(24.dp)) }, // 로딩 중 표시
                // error = { Image(painter = /* 에러 이미지 Painter */, contentDescription = "Error") } // 에러 발생 시 표시
            )
        }
    }
}
