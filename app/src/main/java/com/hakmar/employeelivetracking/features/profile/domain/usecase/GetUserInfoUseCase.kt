package com.hakmar.employeelivetracking.features.profile.domain.usecase

import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.features.profile.domain.repository.ProfileRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(): Flow<Resource<User>> {
        return repository.getUserInfo()
    }
}