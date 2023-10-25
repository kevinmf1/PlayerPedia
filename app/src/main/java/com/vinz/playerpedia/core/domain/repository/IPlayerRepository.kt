package com.vinz.playerpedia.core.domain.repository

import androidx.lifecycle.LiveData
import com.vinz.playerpedia.core.data.Resource
import com.vinz.playerpedia.core.domain.model.Player

interface IPlayerRepository {

    fun insertPlayer(player: Player)

    fun deletePlayer(player: Player)

    fun updatePlayer(player: Player)

    fun getAllPlayer(): LiveData<List<Player>>

    fun getPlayerById(playerId: Int): LiveData<Player>

}