package com.hakmar.employeelivetracking.common.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import com.hakmar.employeelivetracking.common.domain.model.Store

fun StoreDto.toStore(): Store {
    return Store(
        id = storeUuid,
        code = storeCode,
        name = storeName,
        completedTask = completedTask?.toIntOrNull() ?: 0,
        taskCount = taskCount.toIntOrNull() ?: 4,
        passedTime = storeTimerStatus ?: "0 dk",
        longtitude = storeLongitude.toDouble(),
        lattitude = storeLatitude.toDouble(),
        areaCode = areaCode,
        marketingManager = marketingManager.toMarketingManager(),
        distirctManager = districtManager.toDistirctManager(),
        regionalManager = regionalDirector.toRegionalManager(),
        isStoreShiftEnable = isStoreShiftDisable,
        address = address ?: "",
    )
}