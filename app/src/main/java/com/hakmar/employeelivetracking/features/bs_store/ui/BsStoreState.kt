package com.hakmar.employeelivetracking.features.bs_store.ui

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.util.TimerState
import kotlin.time.Duration.Companion.minutes

data class BsStoreState(
    var isLoading: Boolean = false,
    var seconds: String = "00",
    var minutes: String = "00",
    var hours: String = "00",
    var isPlaying: TimerState = TimerState.Idle,
    var initialTime: Int = 1,
    var maxValueOfTime: Int = 5.minutes.inWholeSeconds.toInt(),
    var storeList: List<Store>? = null
)
