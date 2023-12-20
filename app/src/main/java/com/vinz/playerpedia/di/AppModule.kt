package com.vinz.playerpedia.di

import android.content.Context
import androidx.work.WorkManager
import com.vinz.dataapp.local.UserRepository
import com.vinz.dataapp.remote.PlayerRepository
import com.vinz.domain.usecase.PlayerUseCase
import com.vinz.domain.usecase.UserUseCase
import com.vinz.presentation.player.PlayerInteractor
import com.vinz.presentation.user.UserInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlayerUseCase(playerRepository: PlayerRepository): PlayerUseCase = PlayerInteractor(playerRepository)

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase = UserInteractor(userRepository)

    @Singleton
    @Provides
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}