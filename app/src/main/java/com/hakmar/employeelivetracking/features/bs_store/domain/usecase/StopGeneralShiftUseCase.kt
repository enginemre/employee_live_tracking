package com.hakmar.employeelivetracking.features.bs_store.domain.usecase

import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StopGeneralShiftUseCase @Inject constructor(
    private val bsStoreRepository: BsStoreRepository
) {
    operator fun invoke(type:PauseType): Flow<Resource<Unit>> {
       return if (type == PauseType.Pause)
            bsStoreRepository.pauseGeneralShift()
        else
            bsStoreRepository.exitGeneralShift()
    }

    enum class PauseType{
        Pause,
        Exit
    }
}