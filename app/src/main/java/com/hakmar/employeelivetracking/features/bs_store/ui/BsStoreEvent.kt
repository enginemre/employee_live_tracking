package com.hakmar.employeelivetracking.features.bs_store.ui

import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.common.domain.model.Store


sealed class BsStoreEvent {
    object Idle : BsStoreEvent()
    data class OnGeneralShiftClick(
        val fusedLocationProviderClient: FusedLocationProviderClient,
        val isLocationPermissionGranted: Boolean
    ) :
        BsStoreEvent()

    data class OnStoreClick(
        val data: Store?,
        val fusedLocationProviderClient: FusedLocationProviderClient,
        val isCameraLocationPermissionGranted: Boolean,
        val isLocationPermissionGranted: Boolean
    ) : BsStoreEvent()

    data class RefreshDashBoard(val store: Store?) : BsStoreEvent()
    data class OnTick(val hour: String, val minute: String, val second: String) : BsStoreEvent()

    object DismissDialog : BsStoreEvent()
    data class PermissionRequest(
        val isCameraPermissionGranted: Boolean,
        val isLocationPermissionGranted: Boolean
    ) : BsStoreEvent()
}
