package com.hakmar.employeelivetracking.features.bs_store.domain.usecase

import com.hakmar.employeelivetracking.common.domain.model.TimerStatus
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGeneralShiftStatusUseCase @Inject constructor(
    private val repository: BsStoreRepository
) {
    operator fun invoke(): Flow<Resource<TimerStatus>> {
        return repository.initGeneralShift()
    }
}