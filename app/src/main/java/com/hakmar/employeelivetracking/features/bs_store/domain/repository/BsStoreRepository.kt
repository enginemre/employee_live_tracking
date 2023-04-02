package com.hakmar.employeelivetracking.features.bs_store.domain.repository

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface BsStoreRepository{
    fun startGeneralShift() : Flow<Resource<Timer>>

    fun pauseGeneralShift() : Flow<Resource<Unit>>

    fun exitGeneralShift() : Flow<Resource<Unit>>

    fun getStores(): Flow<Resource<List<Store>>>
}