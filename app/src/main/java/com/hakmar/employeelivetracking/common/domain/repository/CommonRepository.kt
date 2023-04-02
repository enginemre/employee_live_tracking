package com.hakmar.employeelivetracking.common.domain.repository

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    fun getAllStores(): Flow<Resource<List<Store>>>
}