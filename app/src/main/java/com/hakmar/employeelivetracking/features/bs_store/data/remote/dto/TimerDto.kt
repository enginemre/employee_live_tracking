package com.hakmar.employeelivetracking.features.bs_store.data.remote.dto


data class TimerDto(
    val hour: Int,
    val minute: Int,
    val progress: Int,
    val second: Int
)