package com.hakmar.employeelivetracking.features.bs_store.ui

import com.hakmar.employeelivetracking.common.domain.model.Store


sealed class BsStoreEvent {
    object Idle : BsStoreEvent()
    object OnGeneralShiftClick : BsStoreEvent()

    object ShowSnackBar : BsStoreEvent()
    data class OnStoreClick(val data: Store?) : BsStoreEvent()
    data class OnTick(val hour: String, val minute: String, val second: String) : BsStoreEvent()
}
