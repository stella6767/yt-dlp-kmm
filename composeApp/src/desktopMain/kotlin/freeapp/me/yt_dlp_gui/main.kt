package freeapp.me.yt_dlp_gui

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import freeapp.me.yt_dlp_gui.presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "yt_dlp_gui",
        state = rememberWindowState(width = 800.dp, height = 700.dp)
    ) {
        App()
    }
}
