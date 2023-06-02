package com.hakmar.employeelivetracking.features.profile.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.UserDto
import com.hakmar.employeelivetracking.features.profile.domain.model.User

fun UserDto.toUser(): User {
    return User(
        nameSurname = this.firstName + " " + this.lastName,
        email = email
    )
}