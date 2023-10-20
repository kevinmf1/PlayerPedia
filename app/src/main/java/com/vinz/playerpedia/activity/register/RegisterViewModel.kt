package com.vinz.playerpedia.activity.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.data.local.entity.UserEntity

class RegisterViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getUserById(id: Int): LiveData<UserEntity> = mUserRepository.getUserById(id)

    fun insert(user: UserEntity) {
        mUserRepository.insert(user)
    }

    fun update(user: UserEntity) {
        mUserRepository.update(user)
    }

    fun delete(user: UserEntity) {
        mUserRepository.delete(user)
    }
}