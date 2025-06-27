package freeapp.me.yt_dlp_gui.presentation.layout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun GlobalErrorDialog(modifier: Modifier = Modifier) {

    // ErrorManager의 currentError 상태를 구독
    //val appError by ErrorManager.currentError.collectAsState()

    // appError가 null이 아니면 다이얼로그 표시
//    appError?.let { error ->
//        AlertDialog(
//            onDismissRequest = { ErrorManager.dismissDialog() }, // 외부 클릭 시 닫기
//            title = { Text(error.title) },
//            text = { Text(error.message) },
//            confirmButton = {
//                TextButton(onClick = { ErrorManager.dismissDialog() }) {
//                    Text("확인")
//                }
//            },
//            modifier = modifier
//            // dismissButton = { /* 필요하다면 여기에 추가 버튼 (예: 재시도, 로그 보기) */ }
//        )
//    }

//    AlertDialog(
//        onDismissRequest = { ErrorManager.dismissDialog() }, // 외부 클릭 시 닫기
//        title = { Text(error.title) },
//        text = { Text(error.message) },
//        confirmButton = {
//            TextButton(onClick = { ErrorManager.dismissDialog() }) {
//                Text("확인")
//            }
//        },
//        modifier = modifier
//        // dismissButton = { /* 필요하다면 여기에 추가 버튼 (예: 재시도, 로그 보기) */ }
//    )
}
