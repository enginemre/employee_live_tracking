package com.hakmar.employeelivetracking.features.pm_store.ui

import com.hakmar.employeelivetracking.common.domain.model.Store

sealed class PmStoreEvent {
    data class OnStoreClick(val storeCardModel: Store) : PmStoreEvent()
}
