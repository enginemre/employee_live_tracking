package com.hakmar.employeelivetracking.common.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import com.hakmar.employeelivetracking.common.domain.model.Store

fun StoreDto.toStore(): Store {
    return Store(
        id = storeUuid,
        code = storeCode,
        name = storeName,
        completedTask = 2,
        taskCount = 5,
        passedTime = "2 saat 12 dk",
        longtitude = storeLongitude.toDouble(),
        lattitude = storeLatitude.toDouble(),
        areaCode = areaCode,
        marketingManager = marketingManager.toMarketingManager(),
        distirctManager = districtManager.toDistirctManager(),
        regionalManager = regionalDirector.toRegionalManager(),
        isStoreShiftEnable = isStoreShiftDisable,
        address = address
    )
}