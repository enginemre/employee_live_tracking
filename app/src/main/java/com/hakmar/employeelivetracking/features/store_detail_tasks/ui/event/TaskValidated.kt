package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event

import com.hakmar.employeelivetracking.common.presentation.ui.SharedEvent

sealed interface TaskValidated : SharedEvent {
    object PostAmountValidated : TaskValidated
    object SteelCaseValidated : TaskValidated
    object StoreInsideValidated : TaskValidated
    object StoreOutsideValidated : TaskValidated
}