package com.hakmar.employeelivetracking.features.pm_store.domain.repository

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface PmStoreRepository {
    fun getPmStores(): Flow<Resource<List<Store>>>
}