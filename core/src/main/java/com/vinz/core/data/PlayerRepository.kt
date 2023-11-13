package com.vinz.core.data

import com.vinz.core.data.source.remote.datasource.RemoteDataSourceImpl
import com.vinz.core.domain.model.PlayerRemote
import com.vinz.core.domain.model.toPlayerRemote
import com.vinz.core.domain.repository.IPlayerRepository
import com.vinz.core.utils.AppExecutors
import com.vinz.core.utils.ResultWrapper
import com.vinz.core.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val playerRemoteDataSource: RemoteDataSourceImpl
) : IPlayerRepository {

//    companion object {
//        @Volatile
//        private var instance: PlayerRepository? = null
//
//        fun getInstance(
//            remoteData: RemoteDataSourceImpl
//        ): PlayerRepository =
//            instance ?: synchronized(this) {
//                instance ?: PlayerRepository(remoteData)
//            }
//    }

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> {
        return proceedFlow {
            playerRemoteDataSource.getPlayers().data.toPlayerRemote()
        }
    }

}