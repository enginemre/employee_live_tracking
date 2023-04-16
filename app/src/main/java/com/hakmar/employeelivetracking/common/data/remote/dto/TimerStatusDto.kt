package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerDto

data class TimerStatusDto(
    @SerializedName("timer")
    val timer: TimerDto,
    @SerializedName("timer_status")
    val timerStatus: String
)