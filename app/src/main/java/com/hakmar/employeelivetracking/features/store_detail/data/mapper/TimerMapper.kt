package com.hakmar.employeelivetracking.features.store_detail.data.mapper

import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerDto
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.util.pad

fun TimerDto.toTimer(): Timer {
    return Timer(
        hour = hour.pad(),
        minute = minute.pad(),
        second = second.pad(),
        progress = progress
    )
}
