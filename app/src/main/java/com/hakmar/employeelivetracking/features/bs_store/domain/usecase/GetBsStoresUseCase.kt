package com.hakmar.employeelivetracking.features.bs_store.domain.usecase

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBsStoresUseCase @Inject constructor(
    private val bsStoreRepository: BsStoreRepository
) {
    operator fun invoke(): Flow<Resource<List<Store>>> {
        return bsStoreRepository.getStores()
    }
}