package com.vinz.playerpedia.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinz.playerpedia.core.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<UserEntity>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): LiveData<UserEntity?>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<UserEntity?>

}