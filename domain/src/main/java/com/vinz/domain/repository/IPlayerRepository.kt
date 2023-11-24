package com.vinz.domain.repository

import com.vinz.domain.model.PlayerRemote
import com.vinz.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow


interface IPlayerRepository {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}