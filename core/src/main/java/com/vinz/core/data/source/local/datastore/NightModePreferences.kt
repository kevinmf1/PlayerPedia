package com.vinz.core.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "night_mode")

class NightModePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val NIGHT_MODE = booleanPreferencesKey("night_mode_key")

    fun getNightModeSettings(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[NIGHT_MODE] ?: false
        }
    }

    suspend fun saveNightModeSettings(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[NIGHT_MODE] = isDarkModeActive
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