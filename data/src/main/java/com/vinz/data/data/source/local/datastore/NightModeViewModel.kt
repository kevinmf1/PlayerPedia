package com.vinz.data.data.source.local.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NightModeViewModel @Inject constructor(private val pref: NightModePreferences) : ViewModel() {

    fun getNightModeSettings(): LiveData<Boolean> {
        return pref.getNightModeSettings().asLiveData()
    }

    fun saveNightModeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveNightModeSettings(isDarkModeActive)
        }
    }

}