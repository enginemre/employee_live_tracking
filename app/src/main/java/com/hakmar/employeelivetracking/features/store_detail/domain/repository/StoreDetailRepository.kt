package com.hakmar.employeelivetracking.features.store_detail.domain.repository

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.model.TimerStatus
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface StoreDetailRepository {

    fun getStoreDetail(storeCode: String): Flow<Resource<Store>>

    fun startStoreShift(storeCode: String, lat: Double, lon: Double): Flow<Resource<Timer>>

    fun pauseStoreShift(storeCode: String): Flow<Resource<Unit>>

    fun resumeStoreShift(storeCode: String): Flow<Resource<Timer>>

    fun cancelStoreShift(storeCode: String, lat: Double, lon: Double): Flow<Resource<Unit>>

    fun initStoreShift(storeCode: String): Flow<Resource<TimerStatus>>
}