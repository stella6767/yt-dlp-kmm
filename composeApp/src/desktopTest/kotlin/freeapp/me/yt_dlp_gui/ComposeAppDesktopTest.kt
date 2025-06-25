package freeapp.me.yt_dlp_gui

import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.launch

class ComposeAppDesktopTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }


    @Test
    fun executeCommand() {

        val service = YTDlpService()

        val command = mutableListOf<String>()
        command.add("yt-dlp")
        command.add("https://www.youtube.com/watch?v=j9k-kbiemoo&ab_channel=SeanLerwill")

        val (first, second) = service.executeCommandSync(command)

        println(first)
        println(second)

    }


}
