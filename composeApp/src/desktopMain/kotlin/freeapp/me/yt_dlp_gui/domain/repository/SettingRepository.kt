package freeapp.me.yt_dlp_gui.domain.repository

import freeapp.me.yt_dlp_gui.domain.model.queue.SettingState

interface SettingRepository {
    fun findSettingState(): SettingState
    fun updateSettingState(settingState: SettingState): SettingState
}
