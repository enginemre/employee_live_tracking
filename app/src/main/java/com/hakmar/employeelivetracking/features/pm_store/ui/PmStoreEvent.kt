package com.hakmar.employeelivetracking.features.pm_store.ui

import com.hakmar.employeelivetracking.features.pm_store.ui.model.PmStoreModel

sealed class PmStoreEvent {
    data class OnStoreClick(val storeCardModel: PmStoreModel) : PmStoreEvent()
}
