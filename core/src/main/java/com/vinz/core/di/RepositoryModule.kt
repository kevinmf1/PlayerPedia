package com.vinz.core.di

//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
//    @Singleton
//    @Provides
//    fun providePlayerRepository(
//        remoteDataSource: RemoteDataSourceImpl
//    ): IPlayerRepository {
//        return PlayerRepository(remoteDataSource)
//    }
//    @Singleton
//    @Provides
//    fun provideUserRepository(
//        localDataSource: UserDatabaseDataSource,
//        appExecutors: AppExecutors
//    ): IUserRepository {
//        return UserRepository(localDataSource, appExecutors)
//    }
//
//}