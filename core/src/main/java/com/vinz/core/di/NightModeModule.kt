package com.vinz.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.vinz.core.data.source.local.datastore.NightModePreferences
import com.vinz.core.data.source.local.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NightModeModule {

    @Singleton
    @Provides
    fun provideNightModePreferences(pref: DataStore<Preferences>): NightModePreferences {
        return NightModePreferences(pref)
    }

    @Singleton
    @Provides
    fun provideAppDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

}