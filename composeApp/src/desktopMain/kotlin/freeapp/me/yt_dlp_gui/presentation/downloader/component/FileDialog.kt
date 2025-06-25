package freeapp.me.yt_dlp_gui.presentation.downloader.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
fun FileDialog(
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Choose a directory", LOAD) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    onCloseRequest(file)
                }
            }
        }
    },
    dispose = FileDialog::dispose
)


fun openFileDialog(
    window: ComposeWindow? = null,
    title: String,
    allowedExtensions: List<String>,
    allowMultiSelection: Boolean = true
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

    val path =
        fileDialog.directory?.let { File(it) }?.absolutePath ?: ""

    return path
}
