package com.hakmar.employeelivetracking.features.store_detail.domain.usecase

import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.features.store_detail.domain.repository.StoreDetailRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResumeStoreShiftUseCase @Inject constructor(
    private val repository: StoreDetailRepository
) {
    operator fun invoke(storeCode: String): Flow<Resource<Timer>> {
        return repository.resumeStoreShift(storeCode = storeCode)
    }
}