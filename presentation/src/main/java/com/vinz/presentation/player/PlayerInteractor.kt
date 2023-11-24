package com.vinz.presentation.player

import com.vinz.domain.model.PlayerRemote
import com.vinz.domain.repository.IPlayerRepository
import com.vinz.domain.usecase.PlayerUseCase
import com.vinz.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerInteractor @Inject constructor(private val playerRepository: IPlayerRepository) :
    PlayerUseCase {

    override fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>> =
        playerRepository.getAllRemotePlayer()

}