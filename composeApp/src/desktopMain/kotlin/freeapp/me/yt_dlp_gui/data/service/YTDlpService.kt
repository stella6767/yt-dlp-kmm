package freeapp.me.yt_dlp_gui.data.service

import freeapp.me.yt_dlp_gui.domain.model.DownloadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class YTDlpService(

) {

    private var currentProcess: Process? = null
    private var currentProcessJob: Job? = null // Job을 추적하여 취소 가능하게 함
    private val processMutex = Mutex()


    suspend fun downloadVideo(
        scope: CoroutineScope,
        url: String,
        fileName: String,
        saveToDirectory: String,
        additionalArguments: String,
        ytDlpPath: String,
        onStateUpdate: (DownloadState) -> Unit
    ): Pair<Int, String> {


        val await = scope.async(Dispatchers.IO) {

            processMutex.withLock {
                onStateUpdate(DownloadState.Downloading("초기화 중...", "")) // 초기 상태 전달
                //currentProcessJob = coroutineContext[Job] // 현재 코루틴 Job을 추적하여 abort에서 취소 가능하게 함

                val command =
                    buildCommand(ytDlpPath, url, fileName, saveToDirectory, additionalArguments)

                println("실행할 명령: ${command.joinToString(" ")}")
                executeCommandSync(command)
            }
        }.await()

        return await
    }


//    // 다운로드 중단 함수
//    fun abortDownload() {
//        if (currentDownloadJob?.isActive == true) {
//            currentDownloadJob?.cancel() // 코루틴 취소
//            currentProcess?.destroyForcibly() // 실제 프로세스도 강제 종료
//            // 상태 업데이트는 downloadVideo의 catch 블록에서 isActive 확인 후 Aborted로 전달됨
//            println("다운로드 중단 요청됨.")
//        } else {
//            println("진행 중인 다운로드가 없습니다.")
//        }
//    }


    fun executeCommandSync(command: List<String>): Pair<Int, String> {


        println("실행할 명령 (블로킹): ${command.joinToString(" ")}")

        val processBuilder = ProcessBuilder(command)
        processBuilder.redirectErrorStream(true) // 에러 스트림을 표준 출력으로 리디렉션하여 함께 읽기

        var process: Process? = null
        var reader: BufferedReader? = null
        val outputStringBuilder = StringBuilder()
        var exitCode = -1

        try {
            process = processBuilder.start()
            reader = BufferedReader(InputStreamReader(process.inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {



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
        }

        return Pair(exitCode, outputStringBuilder.toString())
    }


    private fun buildCommand(
        ytDlpPath: String,
        url: String,
        fileName: String,
        saveToDirectory: String,
        additionalArguments: String
    ): List<String> {

        val command = mutableListOf<String>()

        // 1. yt-dlp 실행 경로 (설정에서 가져오거나 기본값 사용)
        command.add(ytDlpPath)

        // 2. 출력 경로 설정 (파일명이 있을 경우만 적용)
        if (fileName.isNotBlank()) {
            val outputPath = "$saveToDirectory${File.separator}$fileName"
            command.add("-o")
            command.add(outputPath)
        } else {
            // 파일명이 없으면 디렉토리만 지정
            command.add("-o")
            command.add("$saveToDirectory${File.separator}%(title)s.%(ext)s")
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

}
