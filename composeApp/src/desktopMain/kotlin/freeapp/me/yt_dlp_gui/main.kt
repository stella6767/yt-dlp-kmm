package freeapp.me.yt_dlp_gui

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import freeapp.me.yt_dlp_gui.app.App
import freeapp.me.yt_dlp_gui.config.di.initKoin

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "yt_dlp_gui",
        state = rememberWindowState(width = 1200.dp, height = 700.dp)
    ) {
        App()
    }
}
