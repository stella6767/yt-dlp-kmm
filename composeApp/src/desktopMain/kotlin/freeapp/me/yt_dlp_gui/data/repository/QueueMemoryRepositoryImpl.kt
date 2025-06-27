package freeapp.me.yt_dlp_gui.data.repository

import freeapp.me.yt_dlp_gui.domain.model.DataError
import freeapp.me.yt_dlp_gui.domain.model.Result
import freeapp.me.yt_dlp_gui.domain.model.queue.QueueItem
import freeapp.me.yt_dlp_gui.domain.repository.QueueRepository
import java.util.concurrent.CopyOnWriteArrayList

class QueueMemoryRepositoryImpl(

) : QueueRepository {
    private val queueItems = CopyOnWriteArrayList<QueueItem>()

    override fun add(item: QueueItem): QueueItem {
        queueItems.add(0, item)

        return item
    }

    override fun addAll(items: List<QueueItem>) {
        queueItems.addAll(items)
    }

    override fun remove(id: String) {
        queueItems.removeAll { it.id == id }
    }

    override fun update(item: QueueItem) {
        val index =
            queueItems.indexOfFirst { it.id == item.id }
        if (index > -1) {
            queueItems[index] = item
        }
    }

    override fun clear() {
        queueItems.clear()
    }

    override fun getAll(): List<QueueItem> = queueItems.toList()

    override fun getById(id: String): QueueItem? = queueItems.find { it.id == id }

    override fun exists(id: String): Boolean = queueItems.any { it.id == id }


}
