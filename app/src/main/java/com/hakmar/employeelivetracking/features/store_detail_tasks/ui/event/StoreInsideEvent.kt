package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event

import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem

sealed class StoreInsideEvent {
    data class OnChecked(val checkItem: CheckItem) : StoreInsideEvent()

    object CompleteCheck : StoreInsideEvent()
}
