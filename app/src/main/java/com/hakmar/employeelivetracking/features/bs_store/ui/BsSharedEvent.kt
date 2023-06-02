package com.hakmar.employeelivetracking.features.bs_store.ui

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.presentation.ui.SharedEvent

sealed interface BsSharedEvent : SharedEvent {

    data class RefreshDashboard(val store: Store) : BsSharedEvent
}