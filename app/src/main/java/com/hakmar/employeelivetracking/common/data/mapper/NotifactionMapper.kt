package com.hakmar.employeelivetracking.common.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.NotifcationDto
import com.hakmar.employeelivetracking.features.notification.domain.model.NotificationItem

fun NotifcationDto.toNotification() = NotificationItem(
    title = title,
    message = description,
    dateText = notifiedDate
)