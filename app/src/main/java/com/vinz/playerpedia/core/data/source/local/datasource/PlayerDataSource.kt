package com.vinz.playerpedia.core.data.source.local.datasource

import androidx.lifecycle.LiveData
import com.vinz.playerpedia.core.data.source.local.entity.PlayerEntity
import com.vinz.playerpedia.core.data.source.local.room.PlayerDao

interface PlayerDataSource {

    fun insertPlayer(player: PlayerEntity)

    fun deletePlayer(player: PlayerEntity)

    fun updatePlayer(player: PlayerEntity)

    fun getAllPlayer(): LiveData<List<PlayerEntity>>

    fun getPlayerById(playerId: Int): LiveData<PlayerEntity>
}

class PlayerDatabaseDataSource(private val playerDao: PlayerDao) :
    PlayerDataSource {

    companion object {
        private var instance: PlayerDatabaseDataSource? = null

        fun getInstance(playerDao: PlayerDao): PlayerDatabaseDataSource =
            instance ?: synchronized(this) {
                instance ?: PlayerDatabaseDataSource(playerDao)
            }
    }

    override fun insertPlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }

    override fun deletePlayer(player: PlayerEntity) {
        playerDao.deletePlayer(player)
    }

    override fun updatePlayer(player: PlayerEntity) {
        playerDao.updatePlayer(player)
    }

    override fun getAllPlayer(): LiveData<List<PlayerEntity>> {
        return playerDao.getAllPlayer()
    }

    override fun getPlayerById(playerId: Int): LiveData<PlayerEntity> {
        return playerDao.getPlayerById(playerId)
    }
}