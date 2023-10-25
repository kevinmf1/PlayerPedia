package com.vinz.playerpedia.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinz.playerpedia.core.data.source.local.entity.PlayerEntity

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: PlayerEntity)

    @Delete
    fun deletePlayer(player: PlayerEntity)

    @Update
    fun updatePlayer(player: PlayerEntity)

    @Query("SELECT * FROM player ORDER BY player_name ASC")
    fun getAllPlayer(): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE id = :playerId")
    fun getPlayerById(playerId: Int): LiveData<PlayerEntity>

}