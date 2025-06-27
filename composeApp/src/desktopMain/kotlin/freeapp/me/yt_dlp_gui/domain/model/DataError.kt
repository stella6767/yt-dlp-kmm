package freeapp.me.yt_dlp_gui.domain.model

sealed interface DataError: Error {

    enum class Remote(
        val msg: String,
    ): DataError {
        REQUEST_TIMEOUT("The request timed out."),
        TOO_MANY_REQUESTS("Your quota seems to be exceeded."),
        NO_INTERNET("Couldn't reach server, please check your internet connection."),
        SERVER("Oops, something went wrong."),
        SERIALIZATION("Couldn't parse data."),
        UNKNOWN("Oops, something went wrong.")
    }

    enum class Local(
        val msg: String,
    ): DataError {
        URL("url format is not correct"),
        DISK_FULL("Oops, it seems like your disk is full."),
        UNKNOWN("Oops, something went wrong.")
    }
}
