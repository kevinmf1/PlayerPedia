package com.vinz.playerpedia.core.utils

import com.vinz.playerpedia.core.data.source.local.entity.PlayerEntity
import com.vinz.playerpedia.core.data.source.local.entity.UserEntity
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.domain.model.User

// Entitiy adalah representasi dari tabel di database
// Domain adalah representasi dari tabel di aplikasi
// Response adalah representasi dari tabel di API

object DataMapper {

    fun userMapEntitiesToDomain(input: List<PlayerEntity>): List<Player> =
        input.map {
            Player(
                playerId = it.playerId,
                playerName = it.playerName,
                playerClub = it.playerClub,
                playerPosition = it.playerPosition,
                playerNationality = it.playerNationality,
                playerDescription = it.playerDescription,
                playerImage = it.playerImage
            )
        }

    fun onePlayerEntityToDomain(input: PlayerEntity): Player =
        Player(
            playerId = input.playerId,
            playerName = input.playerName,
            playerClub = input.playerClub,
            playerPosition = input.playerPosition,
            playerNationality = input.playerNationality,
            playerDescription = input.playerDescription,
            playerImage = input.playerImage
        )

    fun playerDomainToEntity(input: Player) = PlayerEntity(
        playerId = input.playerId,
        playerName = input.playerName,
        playerClub = input.playerClub,
        playerPosition = input.playerPosition,
        playerNationality = input.playerNationality,
        playerDescription = input.playerDescription,
        playerImage = input.playerImage
    )

    fun userDomainToUserEntity(input: User) = UserEntity(
        id = input.id ?: 0,
        name = input.name,
        username = input.username,
        email = input.email,
        phone = input.phone,
        password = input.password
    )

    fun userLoginEntityToUserDomain(input: UserEntity?) =
        if (input == null) {
            null
        } else {
            User(
                id = input.id,
                name = input.name,
                username = input.username,
                email = input.email,
                phone = input.phone,
                password = input.password
            )
        }

    fun userEntityToUserDomain(input: UserEntity) =
        User(
            id = input.id,
            name = input.name,
            username = input.username,
            email = input.email,
            phone = input.phone,
            password = input.password
        )
}