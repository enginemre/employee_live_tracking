package com.hakmar.employeelivetracking.features.store_detail.ui

import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel

sealed class StoreDetailEvent {
    data class OnTaskClick(val taskModel: TaskModel) : StoreDetailEvent()
    data class OnTick(val hour: String, val minute: String, val second: String) : StoreDetailEvent()
    data class OnTaskCompleted(val taskName: String) : StoreDetailEvent()
    data class OnActionButtonClick(
        val storeCode: String,
        val fusedLocationProviderClient: FusedLocationProviderClient
    ) : StoreDetailEvent()

    data class OnStopButtonClick(val fusedLocationProviderClient: FusedLocationProviderClient) :
        StoreDetailEvent()

}
