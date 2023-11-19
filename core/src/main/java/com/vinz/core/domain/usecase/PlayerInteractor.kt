package com.vinz.core.domain.usecase

import com.vinz.core.domain.model.PlayerRemote
import com.vinz.core.domain.repository.IPlayerRepository
import com.vinz.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerInteractor @Inject constructor(private val playerRepository: IPlayerRepository) : PlayerUseCase {

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> =
        playerRepository.getAllRemotePlayer()

}