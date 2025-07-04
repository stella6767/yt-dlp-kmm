package freeapp.me.yt_dlp_gui.data.service

import freeapp.me.yt_dlp_gui.data.dto.YtDlpMetadata
import freeapp.me.yt_dlp_gui.domain.model.DataError
import freeapp.me.yt_dlp_gui.domain.model.Result
import freeapp.me.yt_dlp_gui.domain.model.queue.*
import freeapp.me.yt_dlp_gui.domain.repository.SettingRepository
import freeapp.me.yt_dlp_gui.domain.util.isValidUrl
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.math.ln
import kotlin.math.pow

class YTDlpService(
    private val settingRepository: SettingRepository,
) {

    private val json = Json { ignoreUnknownKeys = true }

    private var currentProcess: Process? = null
    private var currentProcessJob: Job? = null // Job을 추적하여 취소 가능하게 함
    private val processMutex = Mutex()


    suspend fun downloadVideo(
        scope: CoroutineScope,
        downloadState: DownloaderState,
        onStateUpdate: (String) -> Unit
    ): Pair<Int, String> {


        val (
            url, fileName,
            additionalArguments,
            downloadType,
            startTime,
            endTime,
        ) = downloadState


        val await = scope.async(Dispatchers.IO) {

            processMutex.withLock {

                onStateUpdate("초기화 중...")

                val command =
                    buildCommand(
                        url, fileName,
                        additionalArguments, downloadType,
                        startTime, endTime,
                    )

                //println("실행할 명령: ${command.joinToString(" ")}")
                onStateUpdate(command.joinToString(" "))
                executeCommandSync(command, onStateUpdate)
            }
        }.await()

        return await
    }


    suspend fun downloadItem(
        item: QueueItem,
        onStateUpdate: (String) -> Unit
    ): Result<Int, DataError> {

        return withContext(Dispatchers.IO) {
            val job = async {
                processMutex.withLock {
                    onStateUpdate("초기화 중...")
                    val command =
                        buildCommand(
                            item.url, item.fileName,
                            item.additionalArguments, item.downloadType,
                            item.startTime, item.endTime,
                        )

                    println("실행할 명령: ${command.joinToString(" ")}")

                    onStateUpdate(command.joinToString(" "))

                    val executeCommandSync = executeCommandSync(command, onStateUpdate)

                    executeCommandSync
                }
            }
            currentProcessJob = job
            val (code, str) = job.await()

            if (code != 0) Result.Error(DataError.Remote.SERIALIZATION) else Result.Success(code)
        }


    }


    // 다운로드 중단 함수
    fun abortDownload(
        onStateUpdate: (String) -> Unit,
    ) {

        currentProcessJob?.cancel()

        if (currentProcess != null) {
            currentProcess?.destroyForcibly()
            onStateUpdate("abort download")
            println("다운로드 중단 요청됨.")
        } else {
            println("진행 중인 다운로드가 없습니다.")
            onStateUpdate("there is no download process")
        }

    }


    fun executeCommandSync(command: List<String>, onStateUpdate: (String) -> Unit): Pair<Int, String> {


        println("실행할 명령 (블로킹): ${command.joinToString(" ")}")

        val processBuilder =
            ProcessBuilder(command).redirectErrorStream(true) // 에러 스트림을 표준 출력으로 리디렉션하여 함께 읽기


        var process: Process? = null
        var reader: BufferedReader? = null
        val outputStringBuilder = StringBuilder()
        var exitCode = -1

        try {
            process = processBuilder.start()
            currentProcess = process

            reader = BufferedReader(InputStreamReader(process.inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {

                onStateUpdate(line ?: "")

                outputStringBuilder.append(line).append("\n") // 각 라인 뒤에 개행 추가
            }

            exitCode = process.waitFor() // 프로세스 종료 대기 (블로킹)

            println("명령 실행 완료. 종료 코드: $exitCode")

        } catch (e: Exception) {
            outputStringBuilder.append("명령 실행 중 오류 발생: ${e.message}\n")
            e.printStackTrace()
            exitCode = -999 // 오류 발생 시 특정 종료 코드 할당
        } finally {
            reader?.close()
            process?.destroyForcibly() // 확실한 종료 (선택 사항)
            currentProcess = null
        }

        return Pair(exitCode, outputStringBuilder.toString())
    }


    private fun buildCommand(
        url: String,
        fileName: String,
        additionalArguments: String,
        downloadType: DownloadType,
        startTime: String,
        endTime: String
    ): List<String> {

        val command = mutableListOf<String>()


        val settingState = settingRepository.findSettingState()


        // 1. yt-dlp 실행 경로
        command.add(settingState.ytDlpPath)

        when (downloadType) {
            DownloadType.AUDIO -> {
                command.add("-x") // 오디오 추출
                if (settingState.audioFormat == AudioFormat.MP3) {
                    command.add("--audio-format")
                    command.add("mp3")
                }
            }

            DownloadType.VIDEO_FULL -> {
                if (settingState.videoFormat == VideoFormat.MP4) {
                    command.add("--merge-output-format")
                    command.add("mp4")
                }
            }

            DownloadType.VIDEO_PARTIAL -> {
                command.add("--download-sections")

                val section = buildString {
                    if (!startTime.isNullOrBlank()) append("$startTime")
                    if (!endTime.isNullOrBlank()) {
                        if (isNotEmpty()) append("-$endTime")
                    }
                }

                if (section.isNotBlank()) {
                    command.add("*$section")
                }


                if (settingState.videoFormat == VideoFormat.MP4) {
                    command.add("--merge-output-format")
                    command.add("mp4")
                }
            }
        }


        // 2. 출력 경로 설정 (파일명이 있을 경우만 적용)
        if (fileName.isNotBlank()) {
            val outputPath = "${settingState.saveToDirectory}${File.separator}$fileName.%(ext)s"
            command.add("-o")
            command.add(outputPath)
        } else {
            // 파일명이 없으면 디렉토리만 지정
            command.add("-o")
            command.add("${settingState.saveToDirectory}${File.separator}%(title)s.%(ext)s")
        }

        // 3. 추가 인자 처리
        if (additionalArguments.isNotBlank()) {
            // 인자를 공백으로 분할하고 유효성 검사
            val args = additionalArguments.split("\\s+".toRegex()).filter { it.isNotBlank() }

            // 위험한 인자 필터링
            val safeArgs = args.filterNot { arg ->
                // 시스템 명령 실행을 방지하기 위한 필터
                arg.contains(";") || arg.contains("&") || arg.contains("|") || arg.contains("`") ||
                        arg.contains("\$(") || arg.startsWith("--exec") || arg.startsWith("--postprocessor-args")
            }

            command.addAll(safeArgs)
        }

        // 4. 진행률 표시 옵션 추가
        command.add("--progress")
        command.add("--newline")
        command.add("--console-title")

        // 5. 최종 URL 추가
        command.add(url)

        return command
    }

    suspend fun extractMetaData(
        //scope: CoroutineScope,
        url: String,
    ): Result<YtDlpMetadata?, DataError> {


        if (!isValidUrl(url)) {
            return Result.Error(DataError.Local.URL)
        }

        val settingState = settingRepository.findSettingState()


        return withContext(Dispatchers.IO) {
            try {
                // yt-dlp 명령: --print-json --skip-download (다운로드 없이 JSON 출력)
                // -s (--simulate) 옵션도 다운로드 없이 정보를 얻는 데 사용됩니다.
                // --flat-playlist: 재생 목록의 경우 항목 자체의 정보만 가져오고 내부 비디오는 가져오지 않음
                // --no-warnings: 경고 메시지 출력 안함
                val command = listOf(
                    settingState.ytDlpPath,
                    "--print",
                    "%(title)s|%(filesize_approx)s",
                    "--no-warnings",
                    url
                )

                val processBuilder =
                    ProcessBuilder(command).redirectErrorStream(true)

                val process =
                    processBuilder.start()

                val output =
                    process.inputStream.bufferedReader().use { it.readText() }

                val exitCode = process.waitFor() // 프로세스 종료 대기

                if (exitCode == 0) {
                    println(output)
                    val (title, size) = parseYtDlpOutput(output)
                    val metadata = YtDlpMetadata(
                        title = title,
                        size = size,
                    )
                    Result.Success(metadata)
                } else {
                    println("Error running yt-dlp. Exit code: $exitCode")
                    println("Output: $output")
                    Result.Error(DataError.Remote.SERIALIZATION)

                }
            } catch (e: Exception) {
                println("IOException: Make sure yt-dlp is installed and in your PATH, or specify its full path.")
                println(e.message)
                Result.Error(DataError.Remote.SERIALIZATION)

            }
        }
    }


    fun parseYtDlpOutput(line: String): Pair<String, String> {
        val parts = line.split("|")
        if (parts.size < 2) return "" to ""

        val sizeBytes =
            parts.last().trim().toLongOrNull() ?: 0L
        val title =
            parts.dropLast(1).joinToString("|").trim()

        val readableSize = humanReadableByteCount(sizeBytes)

        return title to readableSize
    }


    fun humanReadableByteCount(bytes: Long): String {
        val unit = 1024
        if (bytes < unit) return "$bytes B"

        val exp = (ln(bytes.toDouble()) / ln(unit.toDouble())).toInt()
        val pre = "KMGTPE"[exp - 1] + "iB"

        return String.format("%.1f %s", bytes / unit.toDouble().pow(exp), pre)
    }

}
