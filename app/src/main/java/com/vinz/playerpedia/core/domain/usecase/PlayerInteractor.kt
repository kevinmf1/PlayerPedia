package com.vinz.playerpedia.core.domain.usecase

import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.repository.IPlayerRepository

class PlayerInteractor(private val playerRepository: IPlayerRepository) : PlayerUseCase {

    override fun insertPlayer(player: Player) = playerRepository.insertPlayer(player)

    override fun deletePlayer(player: Player) = playerRepository.deletePlayer(player)

    override fun updatePlayer(player: Player) = playerRepository.updatePlayer(player)

    override fun getAllPlayer() = playerRepository.getAllPlayer()

    override fun getPlayerById(playerId: Int) = playerRepository.getPlayerById(playerId)
}