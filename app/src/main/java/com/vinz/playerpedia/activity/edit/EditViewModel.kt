package com.vinz.playerpedia.activity.edit

import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase

class EditViewModel (private val playerUseCase: PlayerUseCase) : ViewModel() {

    fun updatePlayer(player: Player) {
        playerUseCase.updatePlayer(player)
    }

}