package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository

import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface SteelCaseRepository {
    fun sendSteelCaseRepository(map: HashMap<String, Any>): Flow<Resource<String>>
}