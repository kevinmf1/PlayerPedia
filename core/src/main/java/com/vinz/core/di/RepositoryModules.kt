package com.vinz.core.di

import com.vinz.core.data.PlayerRepository
import com.vinz.core.data.UserRepository
import com.vinz.core.domain.repository.IPlayerRepository
import com.vinz.core.domain.repository.IUserRepository
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