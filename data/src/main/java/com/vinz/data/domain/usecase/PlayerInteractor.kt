package com.vinz.data.domain.usecase

import com.vinz.data.domain.model.PlayerRemote
import com.vinz.data.domain.repository.IPlayerRepository
import com.vinz.data.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerInteractor @Inject constructor(private val playerRepository: IPlayerRepository) : PlayerUseCase {

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> =
        playerRepository.getAllRemotePlayer()

}