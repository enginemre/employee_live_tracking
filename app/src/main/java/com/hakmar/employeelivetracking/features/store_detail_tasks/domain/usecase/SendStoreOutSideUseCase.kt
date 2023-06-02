package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase

import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.StoreOutsideRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendStoreOutSideUseCase @Inject constructor(
    private val storeOutsideRepository: StoreOutsideRepository
) {
    operator fun invoke(storeCode: String, checkList: List<CheckItem>): Flow<Resource<Unit>> {
        return storeOutsideRepository.sendCheckList(storeCode, checkList)
    }

}