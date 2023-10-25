package com.vinz.playerpedia.core.domain.usecase

import androidx.lifecycle.LiveData
import com.vinz.playerpedia.core.domain.model.Player

interface PlayerUseCase {
    fun insertPlayer(player: Player)

    fun deletePlayer(player: Player)

    fun updatePlayer(player: Player)

    fun getAllPlayer(): LiveData<List<Player>>

    fun getPlayerById(playerId: Int): LiveData<Player>
}