package com.vinz.core.di

//@Module
//@InstallIn(SingletonComponent::class)
//object DataSourceModule {
//
//    @Singleton
//    @Provides
//    fun provideUserDataSource(userDao: UserDao) : UserDataSource {
//        return UserDatabaseDataSource(userDao)
//    }
//
//    @Singleton
//    @Provides
//    fun provideRemoteDataSource(service: APIService) : RemoteDataSource {
//        return RemoteDataSourceImpl(service)
//    }
//}