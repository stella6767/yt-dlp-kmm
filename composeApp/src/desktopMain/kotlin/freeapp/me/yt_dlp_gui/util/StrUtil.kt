package freeapp.me.yt_dlp_gui.util

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
