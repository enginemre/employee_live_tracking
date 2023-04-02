package com.hakmar.employeelivetracking.features.profile.domain.repository

import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserInfo(): Flow<Resource<User>>

    fun updateUserInfo(
        oldPassword: String,
        newPassword: String,
        email: String
    ): Flow<Resource<User>>
}