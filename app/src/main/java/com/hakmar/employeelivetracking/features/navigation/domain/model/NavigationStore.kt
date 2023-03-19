package com.hakmar.employeelivetracking.features.navigation.domain.model

data class NavigationStore(
    val lat: Double,
    val lon: Double,
    val storeName: String,
    val storeCode: String,
)
