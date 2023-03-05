package com.hakmar.employeelivetracking.features.auth.data.repository

import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(

) : LoginRepository {
    override fun login(userCode: String, password: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            delay(2000)
            emit(Resource.Success(true))
        }
    }
}