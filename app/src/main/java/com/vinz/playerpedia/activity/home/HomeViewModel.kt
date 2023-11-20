package com.vinz.playerpedia.activity.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vinz.data.domain.usecase.PlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val playerUseCase: PlayerUseCase) : ViewModel() {

    val player = playerUseCase.getAllRemotePlayer().asLiveData(Dispatchers.IO)

}