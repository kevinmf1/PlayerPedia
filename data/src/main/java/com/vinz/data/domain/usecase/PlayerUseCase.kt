package com.vinz.data.domain.usecase

import com.vinz.data.domain.model.PlayerRemote
import com.vinz.data.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PlayerUseCase {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}