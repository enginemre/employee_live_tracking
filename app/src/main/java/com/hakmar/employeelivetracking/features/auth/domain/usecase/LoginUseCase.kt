package com.hakmar.employeelivetracking.features.auth.domain.usecase

import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(userCode: String, password: String): Flow<Resource<User>> {
        return loginRepository.login(userCode, password)
    }
}