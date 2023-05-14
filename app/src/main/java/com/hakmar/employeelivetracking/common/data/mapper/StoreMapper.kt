package com.hakmar.employeelivetracking.common.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import com.hakmar.employeelivetracking.common.domain.model.Store

fun StoreDto.toStore(): Store {
    return Store(
        id = storeUuid,
        code = storeCode,
        name = storeName,
        completedTask = completedTask?.toIntOrNull() ?: 0,
        taskCount = taskCount?.toIntOrNull() ?: 4,
        passedTime = storeTimerStatus ?: "0 dk",
        longtitude = storeLongitude.toDouble(),
        lattitude = storeLatitude.toDouble(),
        areaCode = areaCode,
        marketingManager = marketingManager.toMarketingManager(),
        distirctManager = districtManager.toDistirctManager(),
        regionalManager = regionalDirector.toRegionalManager(),
        isStoreShiftEnable = isStoreShiftDisable,
        address = address ?: "",
        taskStatus = hashMapOf<String, Boolean>(
            POS_STATUS to (postAmountStatus ?: false),
            STEEL_STATUS to (steelCaseStatus ?: false),
            STORE_INSIDE_STATUS to (storeOpeningStatus ?: false),
            STORE_OUTSIDE_STATUS to (storeClosingStatus ?: false)
        )
    )
}

const val POS_STATUS = "pos_status"
const val STEEL_STATUS = "steel_status"
const val STORE_INSIDE_STATUS = "store_inside_status"
const val STORE_OUTSIDE_STATUS = "store_outside_status"