package com.vinz.playerpedia.data.local.repository

import com.vinz.playerpedia.data.local.datasource.PlayerDataSource
import com.vinz.playerpedia.data.local.entity.PlayerEntity

class PlayerRepository(
    private val dataSource: PlayerDataSource
) {

    fun insertPlayer(player: PlayerEntity) {
        dataSource.insertPlayer(player)
    }

    fun deletePlayer(player: PlayerEntity) {
        dataSource.deletePlayer(player)
    }

    fun updatePlayer(player: PlayerEntity) {
        dataSource.updatePlayer(player)
    }

    fun getAllPlayer() = dataSource.getAllPlayer()

    fun getPlayerById(playerId: Int) = dataSource.getPlayerById(playerId)

}