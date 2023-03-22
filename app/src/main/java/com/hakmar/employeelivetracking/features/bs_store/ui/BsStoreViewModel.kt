package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.lifecycle.ViewModel
import com.hakmar.employeelivetracking.common.service.GeneralShiftServiceManager
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.TimerState
import com.hakmar.employeelivetracking.util.convertStringToDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BsStoreViewModel @Inject constructor(
    private val generalShiftServiceManager: GeneralShiftServiceManager
) : ViewModel() {


    private var _state = MutableStateFlow(BsStoreState())
    val state = _state.asStateFlow()

    private fun start() {
        _state.update {
            it.copy(
                isPlaying = TimerState.Started
            )
        }
        val pausedTime =
            convertStringToDuration(state.value.seconds, state.value.minutes, state.value.hours)
        if (state.value.isPlaying == TimerState.Idle) {
            pausedTime?.let {
                generalShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_GENERAL_SHIFT_TIME_START,
                    it.toString()
                )
            } ?: kotlin.run {
                generalShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_GENERAL_SHIFT_TIME_START
                )
            }
        } else {
            pausedTime?.let {
                generalShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_GENERAL_SHIFT_TIME_START,
                    it.toString()
                )
            } ?: kotlin.run {
                generalShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_GENERAL_SHIFT_TIME_START
                )
            }
        }
    }

    private fun pause() {
        _state.update {
            it.copy(
                isPlaying = TimerState.Stoped
            )
        }
        generalShiftServiceManager.triggerForegroundService(AppConstants.ACTION_GENERAL_SHIFT_TIME_STOP)
    }

    fun generalShiftButtonClick() {
        if (state.value.isPlaying == TimerState.Idle || state.value.isPlaying == TimerState.Stoped) {
            start()
        } else {
            pause()
        }
    }

    fun onTick(h: String, m: String, s: String) {
        _state.update {
            it.copy(
                seconds = s,
                minutes = m,
                hours = h,
                isPlaying = TimerState.Started,
                initialTime = convertStringToDuration(s, m, h)?.inWholeSeconds?.toInt() ?: 1
            )
        }
    }

}