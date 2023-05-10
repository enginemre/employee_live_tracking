package com.hakmar.employeelivetracking.features.bs_store.ui

import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.common.domain.model.Store


sealed class BsStoreEvent {
    object Idle : BsStoreEvent()
    data class OnGeneralShiftClick(val fusedLocationProviderClient: FusedLocationProviderClient) :
        BsStoreEvent()

    data class OnStoreClick(
        val data: Store?,
        val fusedLocationProviderClient: FusedLocationProviderClient
    ) : BsStoreEvent()

    data class OnTick(val hour: String, val minute: String, val second: String) : BsStoreEvent()
}
