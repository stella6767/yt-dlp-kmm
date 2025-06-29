package freeapp.me.yt_dlp_gui

import androidx.compose.ui.window.application
import freeapp.me.yt_dlp_gui.app.App
import freeapp.me.yt_dlp_gui.config.di.initKoin
import kotlin.system.exitProcess


fun main() = application {

    initKoin()

    App({
        exitApplication()
        exitProcess(0)
    })
}


