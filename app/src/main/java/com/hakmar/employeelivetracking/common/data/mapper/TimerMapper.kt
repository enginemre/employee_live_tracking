package com.hakmar.employeelivetracking.common.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.TimerStatusDto
import com.hakmar.employeelivetracking.common.domain.model.TimerStatus
import com.hakmar.employeelivetracking.features.bs_store.data.mapper.toTimer

fun TimerStatusDto.toTimerStatus(): TimerStatus {
    return TimerStatus(
        status = timerStatus,
        timer = timer.toTimer()
    )
}