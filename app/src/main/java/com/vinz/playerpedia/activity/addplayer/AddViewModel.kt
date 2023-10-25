package com.vinz.playerpedia.activity.addplayer

import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase

class AddViewModel (private val playerUseCase: PlayerUseCase) : ViewModel() {

    fun addPlayer(player: Player) {
        playerUseCase.insertPlayer(player)
    }

}