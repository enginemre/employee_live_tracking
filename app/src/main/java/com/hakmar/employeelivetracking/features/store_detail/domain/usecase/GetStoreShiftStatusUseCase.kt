package com.hakmar.employeelivetracking.features.store_detail.domain.usecase

import com.hakmar.employeelivetracking.common.domain.model.TimerStatus
import com.hakmar.employeelivetracking.features.store_detail.domain.repository.StoreDetailRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreShiftStatusUseCase @Inject constructor(
    private val repository: StoreDetailRepository
) {
    operator fun invoke(storeCode: String): Flow<Resource<TimerStatus>> {
        return repository.initStoreShift(storeCode)
    }
}