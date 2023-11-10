package com.vinz.playerpedia.core.domain.repository

import androidx.lifecycle.LiveData
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.model.PlayerRemote
import com.vinz.playerpedia.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface IPlayerRepository {

    fun getAllRemotePlayer(): Flow<ResultWrapper<List<PlayerRemote>>>

    fun insertPlayer(player: Player)

    fun deletePlayer(player: Player)

    fun updatePlayer(player: Player)

    fun getAllPlayer(): LiveData<List<Player>>

    fun getPlayerById(playerId: Int): LiveData<Player>

}