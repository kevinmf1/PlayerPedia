package com.vinz.playerpedia.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "player")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val playerId: Int,

    @ColumnInfo(name = "player_name")
    val playerName: String,

    @ColumnInfo(name = "player_club")
    val playerClub: String,

    @ColumnInfo(name = "player_position")
    val playerPosition: String,

    @ColumnInfo(name = "player_nationaly")
    val playerNationality: String,

    @ColumnInfo(name = "player_description")
    val playerDescription: String,

    @ColumnInfo(name = "player_image")
    val playerImage: File,

    )