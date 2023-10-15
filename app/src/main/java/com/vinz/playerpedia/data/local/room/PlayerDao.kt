package com.vinz.playerpedia.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinz.playerpedia.data.local.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: PlayerEntity)

    @Delete
    fun deletePlayer(player: PlayerEntity)

    @Update
    fun updatePlayer(player: PlayerEntity)

    @Query("SELECT * FROM player ORDER BY player_name ASC")
    fun getAllPlayer(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE id = :playerId")
    fun getPlayerById(playerId: Int): Flow<PlayerEntity>

}