package com.hakmar.employeelivetracking.features.auth.domain.repository

import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(userCode: String, password: String): Flow<Resource<Boolean>>
}