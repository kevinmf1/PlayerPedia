package com.vinz.playerpedia.core.di

import android.content.Context
import com.vinz.playerpedia.core.data.PlayerRepository
import com.vinz.playerpedia.core.data.UserRepository
import com.vinz.playerpedia.core.data.source.local.datasource.PlayerDatabaseDataSource
import com.vinz.playerpedia.core.data.source.local.datasource.UserDatabaseDataSource
import com.vinz.playerpedia.core.data.source.local.room.AppDatabase
import com.vinz.playerpedia.core.domain.repository.IPlayerRepository
import com.vinz.playerpedia.core.domain.repository.IUserRepository
import com.vinz.playerpedia.core.domain.usecase.PlayerInteractor
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase
import com.vinz.playerpedia.core.domain.usecase.UserInteractor
import com.vinz.playerpedia.core.domain.usecase.UserUseCase
import com.vinz.playerpedia.core.utils.AppExecutors

object Injection {
    private fun providePlayerRepository(context: Context): IPlayerRepository {
        val database = AppDatabase.getInstance(context)

        val localDataSource = PlayerDatabaseDataSource.getInstance(database.playerDao())
        val appExecutors = AppExecutors()

        return PlayerRepository.getInstance(localDataSource, appExecutors)
    }

    private fun provideUserRepository(context: Context): IUserRepository {
        val database = AppDatabase.getInstance(context)

        val localDataSource = UserDatabaseDataSource.getInstance(database.userDao())
        val appExecutors = AppExecutors()

        return UserRepository.getInstance(localDataSource, appExecutors)
    }

    fun providePlayerUseCase(context: Context): PlayerUseCase {
        val repository = providePlayerRepository(context)
        return PlayerInteractor(repository)
    }

    fun provideUserUseCase(context: Context): UserUseCase {
        val repository = provideUserRepository(context)
        return UserInteractor(repository)
    }
}