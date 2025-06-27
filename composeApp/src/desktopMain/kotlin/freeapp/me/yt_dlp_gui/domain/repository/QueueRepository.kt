package freeapp.me.yt_dlp_gui.domain.repository

import freeapp.me.yt_dlp_gui.domain.model.QueueItem

interface QueueRepository {

    fun add(item: QueueItem) : QueueItem

    fun addAll(items: List<QueueItem>)

    fun remove(id: String)

    fun update(item: QueueItem)

    fun clear()

    fun getAll(): List<QueueItem>

    fun getById(id: String): QueueItem?

    fun exists(id: String): Boolean
}
