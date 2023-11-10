package com.vinz.playerpedia.core.domain.repository

import com.vinz.playerpedia.core.domain.model.PlayerRemote
import com.vinz.playerpedia.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface IPlayerRepository {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}