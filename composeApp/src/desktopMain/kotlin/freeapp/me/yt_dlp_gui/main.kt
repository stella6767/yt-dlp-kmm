package freeapp.me.yt_dlp_gui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import freeapp.me.yt_dlp_gui.app.App
import freeapp.me.yt_dlp_gui.config.di.initKoin
import freeapp.me.yt_dlp_gui.domain.repository.SettingRepository
import freeapp.me.yt_dlp_gui.presentation.global.component.MTopAppBar
import io.kanro.compose.jetbrains.expui.theme.DarkTheme
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import io.kanro.compose.jetbrains.expui.theme.Theme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform
import yt_dlp_gui.composeapp.generated.resources.Res
import yt_dlp_gui.composeapp.generated.resources.github
import kotlin.system.exitProcess


fun main() = application {

    initKoin()

    App({
        exitApplication()
        exitProcess(0)
    })
}


