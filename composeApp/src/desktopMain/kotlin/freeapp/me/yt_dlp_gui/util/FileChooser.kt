package freeapp.me.yt_dlp_gui.util

import androidx.compose.ui.awt.ComposeWindow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.io.File
import javax.swing.JFileChooser
import javax.swing.SwingUtilities
import kotlin.coroutines.resume

object FileChooser {

    suspend fun chooseDirectory(): String? = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            SwingUtilities.invokeLater { // Swing 대화상자는 EDT에서 실행
                val fileChooser = JFileChooser().apply {
                    fileSelectionMode = JFileChooser.DIRECTORIES_ONLY // 폴더만 선택하도록 설정
                    dialogTitle = "다운로드 폴더 선택" // 대화상자 제목
                    // 기본 시작 디렉토리를 사용자의 홈 디렉토리로 설정
                    currentDirectory = File(System.getProperty("user.home"))
                }

                val result = fileChooser.showOpenDialog(null)

                if (result == JFileChooser.APPROVE_OPTION) {
                    val selectedFile = fileChooser.selectedFile
                    continuation.resume(selectedFile?.absolutePath)
                } else {
                    continuation.resume(null)
                }
            }
        }
    }


    fun chooseFile(
        window: ComposeWindow? = null,
        title: String,
        allowedExtensions: List<String>,
        allowMultiSelection: Boolean = false
    ): String {

        val fileDialog = FileDialog(window, title, FileDialog.LOAD).apply {
            isMultipleMode = allowMultiSelection
            // windows
            file =
                allowedExtensions.joinToString(";") { "*$it" } // e.g. '*.jpg'
            // linux
            setFilenameFilter { _, name ->
                allowedExtensions.any {
                    name.endsWith(it)
                }
            }
            isVisible = true
        }


        val file = fileDialog?.file
        val directory = fileDialog?.directory

        return if (file != null && directory != null ) {
            directory + file
        } else ""

    }


}
