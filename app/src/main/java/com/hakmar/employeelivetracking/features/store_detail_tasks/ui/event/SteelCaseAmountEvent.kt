package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event

sealed class SteelCaseAmountEvent {
    data class OnTextChange(val changedValue: String, val type: SteelCaseAmountField) :
        SteelCaseAmountEvent()

    data class OnCompleteSteelCaseAmount(val storeCode: String) : SteelCaseAmountEvent()
}

enum class SteelCaseAmountField {
    Banknotes,
    Coins,
    TotalPos,
    TotalPayments,
    SteelCaseAmount,
    Difference
}
