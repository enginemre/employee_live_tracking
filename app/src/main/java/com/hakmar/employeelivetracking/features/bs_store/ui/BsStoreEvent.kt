package com.hakmar.employeelivetracking.features.bs_store.ui

import com.hakmar.employeelivetracking.features.bs_store.domain.model.BsStore


sealed class BsStoreEvent {
    object OnGeneralShiftClick : BsStoreEvent()
    data class OnStoreClick(val data : BsStore) : BsStoreEvent()
    data class OnTick(val hour :String,val minute: String,val second:String) : BsStoreEvent()
}
