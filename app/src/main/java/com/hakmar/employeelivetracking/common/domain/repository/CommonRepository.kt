package com.hakmar.employeelivetracking.common.domain.repository

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.features.notification.domain.model.NotificationItem
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    fun getAllStores(): Flow<Resource<List<Store>>>

    fun getNotifications(): Flow<Resource<List<NotificationItem>>>

    fun sendFCMToken(token: String): Flow<Resource<Unit>>
}