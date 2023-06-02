package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase

import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.StoreOutsideRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreOutsideUseCase @Inject constructor(
    private val repository: StoreOutsideRepository
) {
    operator fun invoke(): Flow<Resource<List<CheckItem>>> {
        return repository.getCheckList()
    }
}