package com.hakmar.employeelivetracking.features.auth.data.repository

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    val dataStore: DataStoreRepository
) : LoginRepository {
    override fun login(userCode: String, password: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            delay(2000)
            dataStore.intPutKey(AppConstants.IS_LOGIN, 1)
            emit(Resource.Success(true))
        }
    }
}