package com.vinz.playerpedia.core.domain.usecase

import com.vinz.playerpedia.core.domain.model.PlayerRemote
import com.vinz.playerpedia.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PlayerUseCase {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

}