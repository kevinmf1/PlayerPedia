package com.vinz.dataapp.remote

import com.vinz.dataapp.remote.datasource.RemoteDataSourceImpl
import com.vinz.dataapp.remote.response.toPlayerRemote
import com.vinz.domain.model.PlayerRemote
import com.vinz.domain.repository.IPlayerRepository
import com.vinz.domain.utils.ResultWrapper
import com.vinz.domain.utils.proceedFlow
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