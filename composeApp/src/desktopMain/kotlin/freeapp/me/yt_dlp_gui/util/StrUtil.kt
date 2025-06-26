package freeapp.me.yt_dlp_gui.util


import java.io.File

fun formatTimeString(time: String): String {
    val formattedTime = buildString {
        time.forEachIndexed { index, char ->
            append(char)
            if (index == 1 && time.length > 2) { // 2자리 입력 후 ":"
                append(':')
            } else if (index == 3 && time.length > 4) { // 4자리 입력 후 ":"
                append(':')
            }
        }
    }
    return formattedTime
}



fun findYtDlpPath(): String {
    return try {
        val command = if (isWindows()) "where yt-dlp" else "which yt-dlp"
        val process = ProcessBuilder(*command.split(" ").toTypedArray())
            .redirectErrorStream(true)
            .start()
        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()
        if (exitCode == 0 && output.isNotBlank()) output else ""
    } catch (e: Exception) {
        ""
    }
}


fun getDefaultDownloadDir(): String {
    val home = System.getProperty("user.home")
    return File(home, "Downloads").absolutePath
}


fun isMac() = System.getProperty("os.name").contains("Mac", ignoreCase = true)
fun isWindows() = System.getProperty("os.name").contains("Windows", ignoreCase = true)
fun isLinux() = System.getProperty("os.name").contains("Linux", ignoreCase = true)
