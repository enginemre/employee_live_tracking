package com.hakmar.employeelivetracking.features.bs_store.data.mapper

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.StoreItemDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerDto
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.util.pad

fun TimerDto.toTimer(): Timer {
    return Timer(
        hour = hour.pad(),
        minute = minute.pad(),
        second = second.pad(),
        progress = progress
    )
}

fun StoreItemDto.toStore(): Store {
    return Store(
        id = id.toString(),
        code = storeCode,
        name = storeName,
        completedTask = 2,
        taskCount = 11,
        passedTime = "2 saat 12 dakika",
        lattitude = storeLatitude.toDouble(),
        longtitude = storeLongitude.toDouble(),
        areaCode = areaCode
    )
}