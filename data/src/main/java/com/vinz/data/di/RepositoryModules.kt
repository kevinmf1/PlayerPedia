package com.vinz.data.di

import com.vinz.data.data.PlayerRepository
import com.vinz.data.data.UserRepository
import com.vinz.data.domain.repository.IPlayerRepository
import com.vinz.data.domain.repository.IUserRepository
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