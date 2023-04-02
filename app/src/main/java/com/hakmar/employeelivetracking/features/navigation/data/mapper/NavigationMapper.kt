package com.hakmar.employeelivetracking.features.navigation.data.mapper

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore

fun Store.toNavigationStore(): NavigationStore {
    return NavigationStore(
        lat = lattitude,
        lon = longtitude,
        storeName = name,
        storeCode = code
    )
}