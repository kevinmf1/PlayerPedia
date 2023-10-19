package com.vinz.playerpedia.data.local.datasource

import com.vinz.playerpedia.data.local.entity.PlayerEntity
import com.vinz.playerpedia.data.local.room.PlayerDao
import kotlinx.coroutines.flow.Flow

interface PlayerDataSource {

    fun insertPlayer(player: PlayerEntity)

    fun deletePlayer(player: PlayerEntity)

    fun updatePlayer(player: PlayerEntity)

    fun getAllPlayer(): Flow<List<PlayerEntity>>

    fun getPlayerById(playerId: Int): Flow<PlayerEntity>
}

class PlayerDatabaseDataSource(private val playerDao: PlayerDao) : PlayerDataSource {
    override fun insertPlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }

    override fun deletePlayer(player: PlayerEntity) {
        playerDao.deletePlayer(player)
    }

    override fun updatePlayer(player: PlayerEntity) {
        playerDao.updatePlayer(player)
    }

    override fun getAllPlayer(): Flow<List<PlayerEntity>> {
        return playerDao.getAllPlayer()
    }

    override fun getPlayerById(playerId: Int): Flow<PlayerEntity> {
        return playerDao.getPlayerById(playerId)
    }
}