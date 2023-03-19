package com.hakmar.employeelivetracking.features.navigation.ui

import android.location.Location
import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore

data class NavigationState(
    var isLoading: Boolean = false,
    var storeList: List<NavigationStore>? = listOf<NavigationStore>(
        NavigationStore(
            40.98520840398029, 29.22647609101577,
            storeName = "Fatih Esenyalı Pendik",
            storeCode = "5004",
        ),
        NavigationStore(
            40.96715318588728, 29.24017077690967,
            storeName = "Selahattinler Gebze",
            storeCode = "5504",
        ),
        NavigationStore(
            40.96838591104279, 29.260204219902207,
            storeName = "Gülsüyü Maltepe",
            storeCode = "5054",
        ),
        NavigationStore(
            41.00391682815467, 29.20109507135916,
            storeName = "Güzelyalı  Pendik",
            storeCode = "5024",
        ),
    ),
    var lastLocation: Location? = null
)
