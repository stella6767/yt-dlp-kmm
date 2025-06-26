package freeapp.me.yt_dlp_gui.app

import kotlinx.serialization.Serializable


sealed interface Route {

    @Serializable
    data object Queue: Route

    @Serializable
    data object Setting: Route

    @Serializable
    data class Downloader(val id: String): Route
}
