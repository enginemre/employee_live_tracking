package com.hakmar.employeelivetracking.features.store_detail.domain.usecase

import com.hakmar.employeelivetracking.features.store_detail.domain.repository.StoreDetailRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CancelStoreShiftUseCase @Inject constructor(
    private val repository: StoreDetailRepository
) {
    operator fun invoke(storeCode: String, lat: Double, lon: Double): Flow<Resource<Unit>> {
        return repository.cancelStoreShift(storeCode, lat, lon)
    }
}