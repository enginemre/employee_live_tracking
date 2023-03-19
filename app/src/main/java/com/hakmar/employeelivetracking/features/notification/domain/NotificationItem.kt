package com.hakmar.employeelivetracking.features.notification.domain

data class NotificationItem(
    val title: String,
    val message: String,
    val onClick: () -> Unit,
    val dateText: String,
)
