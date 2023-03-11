package com.hakmar.employeelivetracking.features.bs_store.ui

import com.hakmar.employeelivetracking.util.TimerState

data class GeneralShiftTimeState(
    var seconds: String = "00",
    var minutes: String = "00",
    var hours: String = "00",
    var isPlaying: TimerState = TimerState.Idle,
    var initialTime: Int = 1,
    var maxValueOfTime: Int = 100
)
