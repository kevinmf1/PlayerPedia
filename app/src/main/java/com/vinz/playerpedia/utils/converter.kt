package com.vinz.playerpedia.utils

import com.vinz.domain.model.PlayerRemote

fun PlayerRemote.toPlayerRemoteSend(): PlayerRemoteSend {
    return PlayerRemoteSend(
        id = this.id,
        name = this.name,
        club = this.club,
        rate = this.rate,
        position = this.position,
        allGoalUntilNow = this.allGoalUntilNow,
        allAssistUntilNow = this.allAssistUntilNow,
        activePlayer = this.activePlayer,
        description = this.description,
        photo = this.photo
    )
}