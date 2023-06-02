package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event

sealed class PosAmountEvent {
    data class OnTextChange(val changedValue: String, val type: PosAmountsFields) : PosAmountEvent()

    data class OnCompletePosAmount(val storeCode: String) : PosAmountEvent()
}


enum class PosAmountsFields {
    PosAmount,
    Difference,
    CashierAmount
}