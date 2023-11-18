package com.vinz.playerpedia.di

import android.content.Context
import androidx.work.WorkManager
import com.vinz.core.data.PlayerRepository
import com.vinz.core.data.UserRepository
import com.vinz.core.domain.usecase.PlayerInteractor
import com.vinz.core.domain.usecase.PlayerUseCase
import com.vinz.core.domain.usecase.UserInteractor
import com.vinz.core.domain.usecase.UserUseCase
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