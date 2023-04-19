package com.hakmar.employeelivetracking.features.notification.ui

import com.hakmar.employeelivetracking.features.notification.domain.model.NotificationItem

data class NotificationState(
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    var notifications: List<NotificationItem> = emptyList()
)
