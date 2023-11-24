package com.vinz.domain.model


data class PlayerRemote(
    val id: Int,
    val name: String,
    val club: String,
    val rate: Double,
    val position: String,
    val allGoalUntilNow: Int,
    val allAssistUntilNow: Int,
    val activePlayer: String,
    val description: String,
    val photo: String
)