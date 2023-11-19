package com.vinz.core.domain.repository

import com.vinz.core.domain.model.PlayerRemote
import com.vinz.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface IPlayerRepository {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}