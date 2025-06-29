package freeapp.me.yt_dlp_gui.data.repository

import freeapp.me.yt_dlp_gui.domain.model.queue.SettingState
import freeapp.me.yt_dlp_gui.domain.repository.SettingRepository
import freeapp.me.yt_dlp_gui.domain.util.findYtDlpPath
import freeapp.me.yt_dlp_gui.domain.util.getDefaultDownloadDir

class SettingMemoryRepositoryImpl(

) : SettingRepository {

    private var state = SettingState(
        ytDlpPath = findYtDlpPath(),
        saveToDirectory = getDefaultDownloadDir(),
    )

    override fun findSettingState(): SettingState {
        return state
    }

    override fun updateSettingState(
        settingState: SettingState
    ): SettingState {
        this.state = settingState
        return this.state
    }


}
