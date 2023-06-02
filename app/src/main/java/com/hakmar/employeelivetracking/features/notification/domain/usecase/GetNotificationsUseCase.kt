package com.hakmar.employeelivetracking.features.notification.domain.usecase

import com.hakmar.employeelivetracking.common.domain.repository.CommonRepository
import com.hakmar.employeelivetracking.features.notification.domain.model.NotificationItem
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val commonRepository: CommonRepository
) {
    operator fun invoke(): Flow<Resource<List<NotificationItem>>> {
        return commonRepository.getNotifications()
    }
}