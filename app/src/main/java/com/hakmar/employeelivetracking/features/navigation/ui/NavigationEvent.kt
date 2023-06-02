package com.hakmar.employeelivetracking.features.navigation.ui

import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore

sealed class NavigationEvent {
    data class OnStoreClick(val data: NavigationStore) : NavigationEvent()
    data class GetLocaiton(val fusedLocationProviderClient: FusedLocationProviderClient) :
        NavigationEvent()
}
