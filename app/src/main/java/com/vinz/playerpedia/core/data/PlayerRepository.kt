package com.vinz.playerpedia.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vinz.playerpedia.core.data.source.local.datasource.PlayerDatabaseDataSource
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.repository.IPlayerRepository
import com.vinz.playerpedia.core.utils.AppExecutors
import com.vinz.playerpedia.core.utils.DataMapper

class PlayerRepository private constructor(
    private val playerDatabaseDataSource: PlayerDatabaseDataSource,
    private val appExecutors: AppExecutors
) : IPlayerRepository {

    companion object {
        @Volatile
        private var instance: PlayerRepository? = null

        fun getInstance(
            playerData: PlayerDatabaseDataSource,
            appExecutors: AppExecutors
        ): PlayerRepository =
            instance ?: synchronized(this) {
                instance ?: PlayerRepository(playerData, appExecutors)
            }
    }

    override fun insertPlayer(player: Player) {
        val playerEntity = DataMapper.playerDomainToEntity(player)
        appExecutors.diskIO().execute { playerDatabaseDataSource.insertPlayer(playerEntity) }
    }

    override fun deletePlayer(player: Player) {
        val playerEntity = DataMapper.playerDomainToEntity(player)
        appExecutors.diskIO().execute { playerDatabaseDataSource.deletePlayer(playerEntity) }
    }

    override fun updatePlayer(player: Player) {
        val playerEntity = DataMapper.playerDomainToEntity(player)
        appExecutors.diskIO().execute { playerDatabaseDataSource.updatePlayer(playerEntity) }
    }

    override fun getAllPlayer(): LiveData<List<Player>> {
        return playerDatabaseDataSource.getAllPlayer().map { DataMapper.userMapEntitiesToDomain(it) }
    }

    override fun getPlayerById(playerId: Int): LiveData<Player> {
        return playerDatabaseDataSource.getPlayerById(playerId)
            .map { DataMapper.onePlayerEntityToDomain(it) }
    }


}