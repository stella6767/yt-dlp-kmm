package freeapp.me.yt_dlp_gui.domain.util


import freeapp.me.yt_dlp_gui.domain.model.DataError
import freeapp.me.yt_dlp_gui.domain.model.queue.DownloadStatus
import freeapp.me.yt_dlp_gui.domain.model.queue.QueueItem
import kotlinx.datetime.LocalDateTime
import java.io.File
import java.net.URI

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


fun isValidUrl(url: String): Boolean {
    return try {
        val parsed = URI(url).toURL()
        parsed.toURI()
        true
    } catch (e: Exception) {
        false
    }
}


fun parseYtdlpProgress(item: QueueItem, logLine: String): QueueItem {
    var updatedItem = item.copy(currentLog = logLine)

    // Example log line: "[download] 14.2% of ~30.02GiB at 6.86MiB/s ETA 01:04:04"
    val downloadProgressRegex =
        "\\[download\\]\\s+([0-9.]+)%\\s+of\\s+~?([0-9.]+)(MiB|GiB|KiB|B)?\\s+at\\s+([0-9.]+)?(MiB/s|GiB/s|KiB/s|B/s)?\\s+ETA\\s+(.*)".toRegex()

    val matchResult = downloadProgressRegex.find(logLine)

    println("$logLine" )

    if (matchResult != null) {
        val (progressStr, _, _, speedStr, _, etaStr) = matchResult.destructured
        val progress = progressStr.toFloatOrNull()?.div(100f) ?: item.progress
        val speed = speedStr.ifBlank { "" } + matchResult.groupValues[5] // Combine speed value with unit
        val eta = etaStr.ifBlank { "" }
        if (speed.isNotBlank()) {
            updatedItem = updatedItem.copy(
                progress = progress,
                speed = speed,
                eta = eta,
                status = DownloadStatus.DOWNLOADING
            )
        }

    } else if (logLine.contains("Destination:")) {
        // Indicate that download is nearing completion or file is being written
        updatedItem = updatedItem.copy(currentLog = logLine)
    } else if (logLine.contains("Error")) {
        updatedItem = updatedItem.copy(status = DownloadStatus.FAILED, currentLog = logLine)
    } else if (logLine.contains("already been downloaded")) {
        //todo
    }

    return updatedItem
}


fun getErrorMessage(error: DataError): String {
    return when (error) {
        is DataError.Remote -> error.msg
        is DataError.Local -> error.msg
    }
}


fun formatDateTime(dateTime: LocalDateTime, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return when (pattern) {
        "yyyy-MM-dd HH:mm:ss" ->
            "${dateTime.year}-${dateTime.monthNumber.toString().padStart(2, '0')}-${
                dateTime.dayOfMonth.toString().padStart(2, '0')
            } " +
                    "${dateTime.hour.toString().padStart(2, '0')}:${
                        dateTime.minute.toString().padStart(2, '0')
                    }:${dateTime.second.toString().padStart(2, '0')}"

        "yyyy-MM-dd" ->
            "${dateTime.year}-${dateTime.monthNumber.toString().padStart(2, '0')}-${
                dateTime.dayOfMonth.toString().padStart(2, '0')
            }"

        "HH:mm:ss" ->
            "${dateTime.hour.toString().padStart(2, '0')}:${
                dateTime.minute.toString().padStart(2, '0')
            }:${dateTime.second.toString().padStart(2, '0')}"

        else -> dateTime.toString()
    }
}
