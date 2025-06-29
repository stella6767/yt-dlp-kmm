package freeapp.me.yt_dlp_gui.config.di



import freeapp.me.yt_dlp_gui.data.repository.QueueMemoryRepositoryImpl
import freeapp.me.yt_dlp_gui.data.repository.SettingMemoryRepositoryImpl
import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.domain.repository.QueueRepository
import freeapp.me.yt_dlp_gui.domain.repository.SettingRepository
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import freeapp.me.yt_dlp_gui.presentation.queue.QueueViewModel
import freeapp.me.yt_dlp_gui.presentation.setting.SettingViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


// 앱의 모든 공통 의존성을 정의하는 Koin 모듈
val appModule = module {
    singleOf(::QueueMemoryRepositoryImpl) { bind<QueueRepository>() }
    singleOf(::SettingMemoryRepositoryImpl) { bind<SettingRepository>() }

    singleOf(::SettingViewModel) // viewModelOf 대신 single로 등록

    single<YTDlpService> { YTDlpService(get()) }
    viewModelOf(::DownloaderViewModel)
    viewModelOf(::QueueViewModel)

}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule) // 공통 모듈과 플랫폼별 모듈을 함께 로드합니다.
    }
}

