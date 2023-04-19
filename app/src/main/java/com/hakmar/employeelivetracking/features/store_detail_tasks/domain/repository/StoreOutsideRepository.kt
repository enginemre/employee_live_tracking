package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository

import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface StoreOutsideRepository {
    fun getCheckList(): Flow<Resource<List<CheckItem>>>

    fun sendCheckList(storeCode: String, list: List<CheckItem>): Flow<Resource<Unit>>
}