//package freeapp.me.yt_dlp_gui.data.service
//
//import freeapp.me.yt_dlp_gui.domain.model.DownloadStatus
//
//import freeapp.me.yt_dlp_gui.domain.model.QueueItem
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.sync.Mutex
//import kotlinx.coroutines.sync.withLock
//
//class DownloadManager(
//    private val ytDlpService: YTDlpService,
//) {
//
//    private val downloadMutex = Mutex()
//
//
//    suspend fun startProcessingQueue(
//
//    ) {
//        downloadMutex.withLock {
//            while (_downloadQueue.value.any { it.status == DownloadStatus.PENDING }) {
//                val nextItem = _downloadQueue.value.firstOrNull { it.status == DownloadStatus.PENDING }
//                if (nextItem != null) {
//                    println("Starting download for: ${nextItem.title}")
//                    updateQueueItem(
//                        nextItem.copy(
//                            status = DownloadStatus.DOWNLOADING,
//                            currentLog = "Starting download..."
//                        )
//                    )
//                    _currentDownloadProgress.value =
//                        nextItem.copy(status = DownloadStatus.DOWNLOADING, currentLog = "Starting download...")
//
//                    val (exitCode, output) = ytDlpService.downloadVideo(
//                        scope = applicationScope, // Use the applicationScope for the actual download
//                        downloadState = nextItem.toDownloaderState(), // Convert QueueItem to DownloaderState
//                        onStateUpdate = { logLine ->
//                            // Parse logLine to update progress, speed, ETA
//                            val updatedItem = parseYtdlpProgress(nextItem, logLine)
//                            updateQueueItem(updatedItem)
//                            _currentDownloadProgress.value = updatedItem // Update for the active download
//                        }
//                    )
//
//                    val finalStatus = if (exitCode == 0) DownloadStatus.COMPLETED else DownloadStatus.FAILED
//                    updateQueueItem(
//                        nextItem.copy(
//                            status = finalStatus,
//                            currentLog = if (exitCode == 0) "Download Completed" else "Download Failed (Code: $exitCode)",
//                            progress = if (exitCode == 0) 1f else nextItem.progress // Set to 100% on success
//                        )
//                    )
//                }
//            }
//            println("All pending downloads processed.")
//        }
//
//    }
//
//    fun abortCurrentDownload() {
//        ytDlpService.abortDownload { logLine ->
//            println("Abort status: $logLine")
//            val currentItem = _currentDownloadProgress.value
//            if (currentItem != null && currentItem.status == DownloadStatus.DOWNLOADING) {
//                updateQueueItem(currentItem.copy(status = DownloadStatus.ABORTED, currentLog = "Download Aborted"))
//            }
//        }
//    }
//
//
//    private fun parseYtdlpProgress(item: QueueItem, logLine: String): QueueItem {
//        var updatedItem = item.copy(currentLog = logLine)
//
//        // Example log line: "[download] 14.2% of ~30.02GiB at 6.86MiB/s ETA 01:04:04"
//        val downloadProgressRegex =
//            "\\[download\\]\\s+([0-9.]+)%\\s+of\\s+~?([0-9.]+)(MiB|GiB|KiB|B)?\\s+at\\s+([0-9.]+)?(MiB/s|GiB/s|KiB/s|B/s)?\\s+ETA\\s+(.*)".toRegex()
//        val matchResult = downloadProgressRegex.find(logLine)
//
//        if (matchResult != null) {
//            val (progressStr, _, _, speedStr, _, etaStr) = matchResult.destructured
//            val progress = progressStr.toFloatOrNull()?.div(100f) ?: item.progress
//            val speed = speedStr.ifBlank { "" } + matchResult.groupValues[5] // Combine speed value with unit
//            val eta = etaStr.ifBlank { "" }
//
//            updatedItem = updatedItem.copy(
//                progress = progress,
//                speed = speed,
//                eta = eta,
//                status = DownloadStatus.DOWNLOADING
//            )
//        } else if (logLine.contains("Destination:")) {
//            // Indicate that download is nearing completion or file is being written
//            updatedItem = updatedItem.copy(currentLog = logLine)
//        } else if (logLine.contains("Error")) {
//            updatedItem = updatedItem.copy(status = DownloadStatus.FAILED, currentLog = logLine)
//        }
//
//        return updatedItem
//    }
//}
