package com.vinz.di

import com.vinz.dataapp.local.UserRepository
import com.vinz.dataapp.remote.PlayerRepository
import com.vinz.domain.repository.IPlayerRepository
import com.vinz.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModules::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Binds
    abstract fun providePlayerRepository(playerRepository: PlayerRepository): IPlayerRepository

    @Binds
    abstract fun provideUserRepository(userRepository: UserRepository): IUserRepository
}