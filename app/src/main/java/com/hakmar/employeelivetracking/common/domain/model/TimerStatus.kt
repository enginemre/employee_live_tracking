package com.hakmar.employeelivetracking.common.domain.model

import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer

data class TimerStatus(
    val status: String,
    val timer: Timer
)
