package com.vinz.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinz.core.data.source.local.entity.UserEntity
import com.vinz.core.utils.FileConverter

@Database(entities = [UserEntity::class], version = 3, exportSchema = false)
@TypeConverters(FileConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}