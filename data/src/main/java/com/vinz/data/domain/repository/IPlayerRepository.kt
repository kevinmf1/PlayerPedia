package com.vinz.data.domain.repository

import com.vinz.data.domain.model.PlayerRemote
import com.vinz.data.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface IPlayerRepository {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}