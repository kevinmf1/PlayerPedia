package com.vinz.domain.usecase

import com.vinz.domain.model.PlayerRemote
import com.vinz.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PlayerUseCase {
    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}