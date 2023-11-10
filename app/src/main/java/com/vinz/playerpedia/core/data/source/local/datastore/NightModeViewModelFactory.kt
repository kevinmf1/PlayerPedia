package com.vinz.playerpedia.core.data.source.local.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NightModeViewModelFactory (private val pref: NightModePreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NightModeViewModel::class.java)) {
            return NightModeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    
}