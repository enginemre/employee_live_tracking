package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase

import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.StoreInsideRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreInsideUseCase @Inject constructor(
    private val storeInsideRepository: StoreInsideRepository
) {
    operator fun invoke(): Flow<Resource<List<CheckItem>>> {
        return storeInsideRepository.getCheckList()
    }
}