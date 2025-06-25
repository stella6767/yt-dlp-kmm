package freeapp.me.yt_dlp_gui.config.di



import freeapp.me.yt_dlp_gui.data.service.YTDlpService
import freeapp.me.yt_dlp_gui.presentation.downloader.DownloaderViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module // Koin 모듈 DSL을 위한 import


// 앱의 모든 공통 의존성을 정의하는 Koin 모듈
val appModule = module {
    single<YTDlpService> { YTDlpService() }
    viewModelOf(::DownloaderViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule) // 공통 모듈과 플랫폼별 모듈을 함께 로드합니다.
    }
}

