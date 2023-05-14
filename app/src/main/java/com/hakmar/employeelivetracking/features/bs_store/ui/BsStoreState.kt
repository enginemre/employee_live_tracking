package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.compose.ui.graphics.Color
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.util.TimerState

data class BsStoreState(
    var isLoading: Boolean = false,
    var seconds: String = "00",
    var minutes: String = "00",
    var hours: String = "00",
    var isPlaying: TimerState = TimerState.Idle,
    var initialTime: Int = 1,
    var maxValueOfTime: Int = 36000,
    var containerColor: Color = Green40,
    var buttonTextColor: Color = Natural110,
    var buttonText: Int = R.string.start,
    var selectedStoreCode: String = "",
)
