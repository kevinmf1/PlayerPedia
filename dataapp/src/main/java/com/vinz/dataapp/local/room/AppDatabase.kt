package com.vinz.dataapp.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinz.dataapp.local.entity.UserEntity
import com.vinz.dataapp.utils.FileConverter

@Database(entities = [UserEntity::class], version = 3, exportSchema = false)
@TypeConverters(FileConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}