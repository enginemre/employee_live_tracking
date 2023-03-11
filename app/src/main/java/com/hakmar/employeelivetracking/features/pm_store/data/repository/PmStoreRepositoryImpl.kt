package com.hakmar.employeelivetracking.features.pm_store.data.repository

import com.hakmar.employeelivetracking.features.pm_store.domain.model.PmStore
import com.hakmar.employeelivetracking.features.pm_store.domain.repository.PmStoreRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PmStoreRepositoryImpl @Inject constructor(

) : PmStoreRepository {
    override fun getPmStores(): Flow<Resource<List<PmStore>>> {
        return flow {
            emit(Resource.Loading())
            delay(1000)
            emit(
                Resource.Success(
                    listOf(
                        PmStore(id = 2, "5004", "Fatih Esenyalı", 2, 4)
                    )
                )
            )
        }
    }
}