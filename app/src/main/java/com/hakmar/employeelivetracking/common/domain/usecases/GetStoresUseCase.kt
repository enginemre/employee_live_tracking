package com.hakmar.employeelivetracking.common.domain.usecases

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.repository.CommonRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    operator fun invoke(): Flow<Resource<List<Store>>> {
        return repository.getAllStores()
    }
}