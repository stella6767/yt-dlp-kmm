package freeapp.me.yt_dlp_gui.config.di



import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import freeapp.me.yt_dlp_gui.presentation.queue.QueueViewModel
import freeapp.me.yt_dlp_gui.presentation.setting.SettingViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


// 앱의 모든 공통 의존성을 정의하는 Koin 모듈
val appModule = module {
    single<YTDlpService> { YTDlpService() }
    viewModelOf(::DownloaderViewModel)
    viewModelOf(::QueueViewModel)
    viewModelOf(::SettingViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule) // 공통 모듈과 플랫폼별 모듈을 함께 로드합니다.
    }
}

