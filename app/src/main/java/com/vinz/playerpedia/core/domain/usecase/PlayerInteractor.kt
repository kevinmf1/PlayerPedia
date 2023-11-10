package com.vinz.playerpedia.core.domain.usecase

import com.vinz.playerpedia.core.domain.model.PlayerRemote
import com.vinz.playerpedia.core.domain.repository.IPlayerRepository
import com.vinz.playerpedia.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

class PlayerInteractor(private val playerRepository: IPlayerRepository) : PlayerUseCase {

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> =
        playerRepository.getAllRemotePlayer()

}