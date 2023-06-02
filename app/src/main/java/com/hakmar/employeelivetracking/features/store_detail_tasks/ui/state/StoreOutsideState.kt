package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state

data class StoreOutsideState(
    var isLoading: Boolean = false,
    var headers: List<String> = listOf(
        "Claim requested credentials",
        "Claim received credentials",
        "Pending Requests"
    )
)