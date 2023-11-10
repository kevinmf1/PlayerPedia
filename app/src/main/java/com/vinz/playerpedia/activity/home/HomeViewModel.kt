package com.vinz.playerpedia.activity.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val playerUseCase: PlayerUseCase) : ViewModel() {

    val player = playerUseCase.getAllRemotePlayer().asLiveData(Dispatchers.IO)

}