package com.hakmar.employeelivetracking.features.bs_store.domain.usecase

import com.hakmar.employeelivetracking.features.bs_store.domain.model.BsStore
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBsStoresUseCase @Inject constructor(
    private val bsStoreRepository: BsStoreRepository
) {
    operator fun invoke(): Flow<Resource<List<BsStore>>> {
        return bsStoreRepository.getStores()
    }
}