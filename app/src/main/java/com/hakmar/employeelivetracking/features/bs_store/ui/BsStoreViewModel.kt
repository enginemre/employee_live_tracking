package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.service.GeneralShiftServiceManager
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.BsStoreUseCases
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.StopGeneralShiftUseCase
import com.hakmar.employeelivetracking.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BsStoreViewModel @Inject constructor(
    private val generalShiftServiceManager: GeneralShiftServiceManager,
    private val bsStoreUseCases: BsStoreUseCases
) : BaseViewModel<BsStoreEvent>() {

    private var _state = MutableStateFlow(BsStoreState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getStores()
    }

    private var startShiftJob: Job? = null
    private var stopShiftJob: Job? = null
    private var getStoresJob: Job? = null

    private fun startGeneralShift() {
        startShiftService()
        startShiftJob?.cancel()
        startShiftJob = bsStoreUseCases.startGeneralShiftUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    startShiftService()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isPlaying = TimerState.Started,
                            seconds = resource.data?.second ?: "00",
                            minutes = resource.data?.minute ?: "00",
                            hours = resource.data?.hour ?: "00"
                        )
                    }

                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.error_shift_not_started),
                            SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun startShiftService() {
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

    fun exitShiftService() {
        stopShiftJob?.cancel()
        startShiftJob =
            bsStoreUseCases.stopGeneralShiftUseCase(type = StopGeneralShiftUseCase.PauseType.Exit)
                .launchIn(viewModelScope)
    }

    private fun pauseShiftService() {
        stopShiftJob?.cancel()
        stopShiftJob =
            bsStoreUseCases.stopGeneralShiftUseCase(StopGeneralShiftUseCase.PauseType.Pause)
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isPlaying = TimerState.Stoped
                                )
                            }
                            generalShiftServiceManager.triggerForegroundService(AppConstants.ACTION_GENERAL_SHIFT_TIME_STOP)
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false
                                )
                            }
                            _uiEvent.send(
                                UiEvent.ShowSnackBar(
                                    message = UiText.StringResorce(R.string.error_shift_not_paused),
                                    SnackBarType.ERROR
                                )
                            )
                        }
                    }
                }
                .handleErrors()
                .launchIn(viewModelScope)
    }


    private fun generalShiftButtonClick() {
        if (state.value.isPlaying == TimerState.Idle || state.value.isPlaying == TimerState.Stoped)
            startGeneralShift()
        else
            pauseGeneralShift()

    }

    private fun pauseGeneralShift() {
        pauseShiftService()
    }

    private fun getStores() {
        getStoresJob?.cancel()
        getStoresJob = bsStoreUseCases.getBsStoresUseCase()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                storeList = resource.data!!
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
            .handleErrors()
            .launchIn(viewModelScope)
    }

    private fun onTick(h: String, m: String, s: String) {
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

    override fun onEvent(event: BsStoreEvent) {
        when (event) {
            BsStoreEvent.OnGeneralShiftClick -> {
                generalShiftButtonClick()
            }
            is BsStoreEvent.OnStoreClick -> {

            }
            is BsStoreEvent.OnTick -> {
                onTick(event.hour, event.minute, event.second)
            }
        }
    }

}