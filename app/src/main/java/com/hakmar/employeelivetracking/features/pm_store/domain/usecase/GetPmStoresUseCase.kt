package com.hakmar.employeelivetracking.features.pm_store.domain.usecase

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.features.pm_store.domain.repository.PmStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPmStoresUseCase @Inject constructor(
    private val repository: PmStoreRepository
) {
    operator fun invoke(): Flow<Resource<List<Store>>> {
        return repository.getPmStores()
    }
}