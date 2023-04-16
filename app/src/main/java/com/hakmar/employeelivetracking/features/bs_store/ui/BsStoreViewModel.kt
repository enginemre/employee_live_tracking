package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.service.GeneralShiftServiceManager
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.BsStoreUseCases
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.TimerState
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import com.hakmar.employeelivetracking.util.convertStringToDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        initInfo()
    }

    private var startShiftJob: Job? = null
    private var pauseShiftJob: Job? = null
    private var resumeShiftJob: Job? = null
    private var getStoresJob: Job? = null
    private var getStatusShiftsJob: Job? = null


    override fun onEvent(event: BsStoreEvent) {
        when (event) {
            BsStoreEvent.OnGeneralShiftClick -> {
                generalShiftButtonClick()
            }

            is BsStoreEvent.OnStoreClick -> {
                event.data?.let {
                    updateStoreCode(it.code)
                }
            }

            is BsStoreEvent.OnTick -> {
                onTick(event.hour, event.minute, event.second)
            }

            BsStoreEvent.Idle -> {
                initInfo()
            }

            BsStoreEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.error_shift_completed),
                            type = SnackBarType.ERROR
                        )
                    )
                }

            }
        }
    }

    private fun startGeneralShift() {
        startShiftJob?.cancel()
        startShiftJob = bsStoreUseCases.startGeneralShiftUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isPlaying = TimerState.Started,
                            seconds = resource.data?.second ?: "00",
                            minutes = resource.data?.minute ?: "00",
                            hours = resource.data?.hour ?: "00",
                            buttonText = R.string.pause,
                            containerColor = Color.Red,
                            buttonTextColor = Color.White,
                            initialTime = resource.data?.progress ?: 1,
                        )
                    }
                    startShiftService()
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

    private fun pauseShiftService() {
        pauseShiftJob?.cancel()
        pauseShiftJob =
            bsStoreUseCases.pauseGeneralShiftUseCase()
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isPlaying = TimerState.Stoped,
                                    buttonText = R.string.start,
                                    containerColor = Green40,
                                    buttonTextColor = Natural110
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
                }.launchIn(viewModelScope)
    }


    private fun generalShiftButtonClick() {
        when (state.value.isPlaying) {
            TimerState.Idle -> startGeneralShift()
            TimerState.Started -> pauseGeneralShift()
            TimerState.Stoped -> resumeGeneralShift()
            else -> {}
        }
    }

    private fun resumeGeneralShift() {
        resumeShiftJob?.cancel()
        resumeShiftJob = bsStoreUseCases.resumeGeneralShiftUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isPlaying = TimerState.Started,
                            seconds = resource.data?.second ?: "00",
                            minutes = resource.data?.minute ?: "00",
                            hours = resource.data?.hour ?: "00",
                            buttonText = R.string.pause,
                            containerColor = Color.Red,
                            buttonTextColor = Color.White,
                            initialTime = resource.data?.progress ?: 1,
                        )
                    }
                    startShiftService()
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

    private fun initInfo() {
        initStatus()
        getStores()
    }

    private fun initStatus() {
        getStatusShiftsJob?.cancel()
        getStatusShiftsJob = bsStoreUseCases.getGeneralShiftStatusUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    resource.data?.let { timerStatus ->
                        when (timerStatus.status) {
                            "started" -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isPlaying = TimerState.Started,
                                        seconds = timerStatus.timer.second,
                                        minutes = timerStatus.timer.minute,
                                        hours = timerStatus.timer.hour,
                                        buttonText = R.string.pause,
                                        containerColor = Color.Red,
                                        buttonTextColor = Color.White,
                                        initialTime = timerStatus.timer.progress,
                                    )
                                }
                                startShiftService()

                            }

                            "paused" -> {
                                _state.update {
                                    it.copy(
                                        isPlaying = TimerState.Stoped,
                                        seconds = timerStatus.timer.second,
                                        minutes = timerStatus.timer.minute,
                                        hours = timerStatus.timer.hour,
                                        initialTime = timerStatus.timer.progress
                                    )
                                }
                            }

                            else -> {

                            }
                        }
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
        }.launchIn(viewModelScope)
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
            .launchIn(viewModelScope)
    }

    private fun onTick(h: String, m: String, s: String) {
        _state.update {
            it.copy(
                seconds = s,
                minutes = m,
                hours = h,
                initialTime = convertStringToDuration(s, m, h)?.inWholeSeconds?.toInt() ?: 1
            )
        }
    }


    private fun updateStoreCode(storeCode: String) {
        _state.update {
            it.copy(
                selectedStoreCode = storeCode
            )
        }
    }

}