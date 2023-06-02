package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state

data class SteelCaseAmountState(
    var isLoading: Boolean = false,
    var banknotes: String = "",
    var coins: String = "",
    var totalPosAmount: String = "",
    var totalPayment: String = "",
    var steelCaseAmount: String = "",
    var difference: String = ""
)
