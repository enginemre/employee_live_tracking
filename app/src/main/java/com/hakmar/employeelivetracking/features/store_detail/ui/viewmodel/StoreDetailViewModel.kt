package com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hakmar.employeelivetracking.common.service.StoreShiftServiceManager
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailState
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.TimerState
import com.hakmar.employeelivetracking.util.convertStringToDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val storeShiftServiceManager: StoreShiftServiceManager
) : ViewModel() {

    private var _state = MutableStateFlow(StoreDetailState())
    val state = _state.asStateFlow()

    init {
        val id: String? = savedStateHandle["storeId"]
        id?.let {
            Log.d("StoreDetail", id)
        }
    }

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
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START,
                    it.toString()
                )
            } ?: kotlin.run {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START
                )
            }
        } else {
            pausedTime?.let {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START,
                    it.toString()
                )
            } ?: kotlin.run {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START
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
        storeShiftServiceManager.triggerForegroundService(AppConstants.ACTION_STORE_SHIFT_TIME_STOP)
    }

    fun actionButtonClick() {
        if (state.value.isPlaying == TimerState.Idle || state.value.isPlaying == TimerState.Stoped) {
            start()
        } else {
            pause()
        }
    }

    fun stopButtonClick() {
        storeShiftServiceManager.triggerForegroundService(AppConstants.ACTION_STORE_SHIFT_TIME_CANCEL)
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