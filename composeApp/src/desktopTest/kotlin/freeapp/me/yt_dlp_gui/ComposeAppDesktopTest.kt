package freeapp.me.yt_dlp_gui

import freeapp.me.yt_dlp_gui.data.repository.QueueMemoryRepositoryImpl
import freeapp.me.yt_dlp_gui.data.repository.SettingMemoryRepositoryImpl
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.domain.model.queue.QueueItem
import freeapp.me.yt_dlp_gui.domain.util.isValidUrl
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

        val service = YTDlpService(SettingMemoryRepositoryImpl())

        val command = mutableListOf<String>()
        command.add("yt-dlp")
        command.add("https://www.youtube.com/watch?v=j9k-kbiemoo&ab_channel=SeanLerwill")

        val (code, str) = service.executeCommandSync(command, { })

        println("$code: $str")

    }


    @Test
    fun extractTitle() {

        val service = YTDlpService(SettingMemoryRepositoryImpl())

        runBlocking {
            val extractTitle =
                service.extractMetaData("https://www.youtube.com/watch?v=j9k-kbiemoo&ab_channel=SeanLerwill")
            println(extractTitle)
        }


    }


    @Test
    fun queRepository(){

        val repositoryImpl = QueueMemoryRepositoryImpl()

        val item1 = QueueItem(title = "1")
        val item2 = QueueItem(title = "2")
        val item3 = QueueItem(title = "3")

        repositoryImpl.add(item2)
        repositoryImpl.add(item1)
        repositoryImpl.add(item3)

        repositoryImpl.getAll().forEach {
            println(it.title)
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

    @Test
    fun dataClassTest() {

        val test1 = Test1("test 1", "seven")

        val copy = test1.copy(title = "test 2")

        println(copy)


    }


    data class Test1(
        val title: String,
        val name: String,
    )


}
