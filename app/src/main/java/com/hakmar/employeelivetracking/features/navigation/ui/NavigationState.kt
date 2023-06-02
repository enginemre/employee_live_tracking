package com.hakmar.employeelivetracking.features.navigation.ui

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore

data class NavigationState(
    var isLoading: Boolean = false,
    var storeList: List<NavigationStore> = emptyList(),
    var lastLocation: Location? = null,
    var cameraPosition: LatLng = LatLng(40.99715375688897, 29.233578755863043)
)
