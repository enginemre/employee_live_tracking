package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state

data class PosAmountState(
    var isLoading: Boolean = false,
    var posAmount: String = "",
    var cashierAmount: String = "",
    var difference: String = ""
)
