package com.vinz.core.domain.usecase

import com.vinz.core.domain.model.PlayerRemote
import com.vinz.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PlayerUseCase {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}