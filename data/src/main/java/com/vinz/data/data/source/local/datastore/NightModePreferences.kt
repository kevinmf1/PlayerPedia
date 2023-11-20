package com.vinz.data.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "night_mode")

class NightModePreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val nightMode = booleanPreferencesKey("night_mode_key")

    fun getNightModeSettings(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[nightMode] ?: false
        }
    }

    suspend fun saveNightModeSettings(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[nightMode] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NightModePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): NightModePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = NightModePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}