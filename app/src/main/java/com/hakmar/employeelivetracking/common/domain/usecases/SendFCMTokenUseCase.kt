package com.hakmar.employeelivetracking.common.domain.usecases

import com.hakmar.employeelivetracking.common.domain.repository.CommonRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendFCMTokenUseCase @Inject constructor(
    private val commonRepository: CommonRepository
) {
    operator fun invoke(token: String): Flow<Resource<Unit>> {
        return commonRepository.sendFCMToken(token)
    }
}