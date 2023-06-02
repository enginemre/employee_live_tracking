package com.hakmar.employeelivetracking.features.notification.ui

sealed class NotificationEvent {
    object OnRefreshNotification : NotificationEvent()
}
