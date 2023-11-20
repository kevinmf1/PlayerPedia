package com.vinz.data.data

import com.vinz.data.data.source.remote.datasource.RemoteDataSourceImpl
import com.vinz.data.domain.model.PlayerRemote
import com.vinz.data.domain.model.toPlayerRemote
import com.vinz.data.domain.repository.IPlayerRepository
import com.vinz.data.utils.ResultWrapper
import com.vinz.data.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val playerRemoteDataSource: RemoteDataSourceImpl
) : IPlayerRepository {

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> {
        return proceedFlow {
            playerRemoteDataSource.getPlayers().data.toPlayerRemote()
        }
    }

}