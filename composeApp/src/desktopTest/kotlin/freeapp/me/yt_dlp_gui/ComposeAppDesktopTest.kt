package freeapp.me.yt_dlp_gui

import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.util.isValidUrl
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

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

        service.executeCommandSync(command, { } )

    }


    @Test
    fun extractTitle() {

        val service = YTDlpService()

        runBlocking {
            val extractTitle =
                service.extractTitle("https://www.youtube.com/watch?v=j9k-kbiemoo&ab_channel=SeanLerwill")
            println(extractTitle)
        }


    }




    @Test
    fun urlTest() {
        println(isValidUrl("https://google.com"))         // true
        println(isValidUrl("ftp://example.com"))           // true
        println(isValidUrl("not a url"))                   // false
        println(isValidUrl("http://"))                     // false
        println(isValidUrl("https://"))                    // false
    }

}
