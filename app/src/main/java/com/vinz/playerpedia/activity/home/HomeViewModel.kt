package com.vinz.playerpedia.activity.home

import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase

class HomeViewModel(private val playerUseCase: PlayerUseCase) : ViewModel() {

    val player = playerUseCase.getAllPlayer()

    fun deletePlayer(player: Player) {
        playerUseCase.deletePlayer(player)
    }
}