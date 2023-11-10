package com.vinz.playerpedia.core.data.source.local.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NightModeViewModel(private val pref: NightModePreferences) : ViewModel() {

    fun getNightModeSettings(): LiveData<Boolean> {
        return pref.getNightModeSettings().asLiveData()
    }

    fun saveNightModeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveNightModeSettings(isDarkModeActive)
        }
    }

}