package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event

import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem

sealed class StoreOutsideEvent {

    data class OnChecked(val checkItem: CheckItem) : StoreOutsideEvent()

    object CompleteCheck : StoreOutsideEvent()
}
