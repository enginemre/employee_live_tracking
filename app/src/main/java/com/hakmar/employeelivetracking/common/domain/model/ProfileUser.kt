package com.hakmar.employeelivetracking.common.domain.model

import com.hakmar.employeelivetracking.features.profile.domain.model.User

data class ProfileUser(
    val phoneNumber: String,
    val registrationNumber: String,
    val role: String,
    val user: User,
    val userId: String
)
