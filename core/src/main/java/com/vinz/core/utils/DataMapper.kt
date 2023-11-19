package com.vinz.core.utils

import com.vinz.core.data.source.local.entity.UserEntity
import com.vinz.core.domain.model.User

// Entitiy adalah representasi dari tabel di database
// Domain adalah representasi dari tabel di aplikasi
// Response adalah representasi dari tabel di API

object DataMapper {

    fun userDomainToUserEntity(input: User) = UserEntity(
        id = input.id,
        name = input.name,
        username = input.username,
        email = input.email,
        phone = input.phone,
        password = input.password,
        image = input.image
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
                password = input.password,
                image = input.image
            )
        }

    fun userEntityToUserDomain(input: UserEntity) =
        User(
            id = input.id,
            name = input.name,
            username = input.username,
            email = input.email,
            phone = input.phone,
            password = input.password,
            image = input.image
        )
}