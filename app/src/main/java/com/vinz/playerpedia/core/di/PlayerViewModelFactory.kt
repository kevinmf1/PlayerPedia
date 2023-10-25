package com.vinz.playerpedia.core.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinz.playerpedia.activity.addplayer.AddViewModel
import com.vinz.playerpedia.activity.edit.EditViewModel
import com.vinz.playerpedia.activity.home.HomeViewModel
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase

class PlayerViewModelFactory private constructor(private val playerUseCase: PlayerUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: PlayerViewModelFactory? = null

        fun getInstance(context: Context): PlayerViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PlayerViewModelFactory(
                    Injection.providePlayerUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(playerUseCase) as T
            }
            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(playerUseCase) as T
            }
            modelClass.isAssignableFrom(EditViewModel::class.java) -> {
                EditViewModel(playerUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}