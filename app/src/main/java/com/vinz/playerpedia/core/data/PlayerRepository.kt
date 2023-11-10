package com.vinz.playerpedia.core.data

import com.vinz.playerpedia.core.data.source.remote.datasource.RemoteDataSourceImpl
import com.vinz.playerpedia.core.domain.model.PlayerRemote
import com.vinz.playerpedia.core.domain.model.toPlayerRemote
import com.vinz.playerpedia.core.domain.repository.IPlayerRepository
import com.vinz.playerpedia.core.utils.AppExecutors
import com.vinz.playerpedia.core.utils.ResultWrapper
import com.vinz.playerpedia.core.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class PlayerRepository private constructor(
    private val playerRemoteDataSource: RemoteDataSourceImpl,
    private val appExecutors: AppExecutors
) : IPlayerRepository {

    companion object {
        @Volatile
        private var instance: PlayerRepository? = null

        fun getInstance(
            remoteData: RemoteDataSourceImpl,
            appExecutors: AppExecutors
        ): PlayerRepository =
            instance ?: synchronized(this) {
                instance ?: PlayerRepository(remoteData, appExecutors)
            }
    }

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> {
        return proceedFlow {
            playerRemoteDataSource.getPlayers().data.toPlayerRemote()
        }
    }

}