package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.lifecycle.ViewModel
import com.hakmar.employeelivetracking.common.service.GeneralShiftServiceManager
import com.hakmar.employeelivetracking.util.AppConstants
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

    private var _generalShiftTimeState = MutableStateFlow(GeneralShiftTimeState())
    val generalShiftTimeState = _generalShiftTimeState.asStateFlow()


    fun start() {
        _state.update {
            it.copy(
                buttonName = "Durdur"
            )
        }
        generalShiftServiceManager.triggerForegroundService(AppConstants.ACTION_GENERAL_SHIFT_TIME_START)
    }


    fun pause() {
        _state.update {
            it.copy(
                buttonName = "Başla"
            )
        }
    }

    fun generalShiftButtonClick() {
        if (state.value.buttonName == "Başla") {
            start()
        } else if (state.value.buttonName == "Durdur") {
            pause()
        } else {
            resume()
        }
    }


    fun resume() {
        _state.update {
            it.copy(
                buttonName = "Durdur"
            )
        }
    }


}