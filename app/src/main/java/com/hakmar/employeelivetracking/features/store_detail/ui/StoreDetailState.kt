package com.hakmar.employeelivetracking.features.store_detail.ui

import com.hakmar.employeelivetracking.util.TimerState
import kotlin.time.Duration.Companion.minutes

data class StoreDetailState(
    var isLoading: Boolean = false,
    var seconds: String = "00",
    var minutes: String = "00",
    var hours: String = "00",
    var isPlaying: TimerState = TimerState.Idle,
    var initialTime: Int = 1,
    var maxValueOfTime: Int = 45.minutes.inWholeSeconds.toInt()
)
