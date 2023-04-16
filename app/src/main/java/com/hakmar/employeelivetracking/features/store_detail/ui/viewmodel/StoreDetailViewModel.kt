package com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.service.StoreShiftServiceManager
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.GetStoreDetailUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.GetStoreShiftStatusUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.PauseStoreShiftUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.StartStoreShiftUseCase
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailEvent
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailState
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
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreRepository: DataStoreRepository,
    private val pauseStoreShiftUseCase: PauseStoreShiftUseCase,
    private val startStoreShiftUseCase: StartStoreShiftUseCase,
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val initStoreShiftStatusUseCase: GetStoreShiftStatusUseCase,
    private val storeShiftServiceManager: StoreShiftServiceManager,
) : BaseViewModel<StoreDetailEvent>() {

    private var _state = MutableStateFlow(StoreDetailState())
    val state = _state.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private var startStoreShiftJob: Job? = null
    private var stopStoreShiftJob: Job? = null
    private var getStoreDetailJob: Job? = null
    private var getStoreStatusJob: Job? = null


    init {
        initStatus("")
        getStoreDetail("")
    }

    override fun onEvent(event: StoreDetailEvent) {
        when (event) {
            is StoreDetailEvent.OnTaskClick -> {
                goTaskScreen(event.taskModel)
            }

            is StoreDetailEvent.OnTick -> {
                onTick(event.hour, event.minute, event.second)
            }

            is StoreDetailEvent.OnActionButtonClick -> {
                actionButtonClick(event.storeCode)
            }

            StoreDetailEvent.OnStopButtonClick -> {
                stopButtonClick()
            }
        }
    }

    private fun initStatus(storeCode: String) {
        getStoreStatusJob?.cancel()
        getStoreStatusJob = initStoreShiftStatusUseCase(storeCode).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    resource.data?.let { timerStatus ->
                        when (timerStatus.status) {
                            "started" -> {
                                start(storeCode)
                            }

                            "paused" -> {
                                _state.update {
                                    it.copy(
                                        seconds = timerStatus.timer.second,
                                        minutes = timerStatus.timer.minute,
                                        hours = timerStatus.timer.hour,
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


    private fun actionButtonClick(storeCode: String) {
        if (state.value.isPlaying == TimerState.Idle || state.value.isPlaying == TimerState.Stoped) {
            start(storeCode)
        } else {
            pause(storeCode)
        }
    }

    private fun start(storeCode: String) {
        startStoreShiftJob?.cancel()
        startStoreShiftJob = startStoreShiftUseCase(storeCode).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            seconds = resource.data?.second ?: "00",
                            hours = resource.data?.hour ?: "00",
                            minutes = resource.data?.minute ?: "00",
                            isPlaying = TimerState.Started,
                        )
                    }
                    startStoreShiftService()
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
                            message = resource.message,
                            type = SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun pause(storeCode: String) {
        pauseStoreShiftService(storeCode = storeCode)
    }

    private fun stopButtonClick() {
        _state.update {
            it.copy(
                isPlaying = TimerState.Idle
            )
        }
        runBlocking {
            dataStoreRepository.intPutKey(AppConstants.IS_STORE_VALIDATE, 0)
            dataStoreRepository.stringPutKey(AppConstants.CURRENT_STORE_CODE, "")
        }
        storeShiftServiceManager.triggerForegroundService(AppConstants.ACTION_STORE_SHIFT_TIME_CANCEL)
    }

    private fun startStoreShiftService() {
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
                    AppConstants.ACTION_GENERAL_SHIFT_TIME_START,
                    it.toString()
                )
            } ?: kotlin.run {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_GENERAL_SHIFT_TIME_START
                )
            }
        }
    }

    private fun pauseStoreShiftService(storeCode: String) {
        stopStoreShiftJob?.cancel()
        stopStoreShiftJob = pauseStoreShiftUseCase(storeCode).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isPlaying = TimerState.Stoped
                        )
                    }
                    storeShiftServiceManager.triggerForegroundService(AppConstants.ACTION_STORE_SHIFT_TIME_STOP)
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
                            message = resource.message,
                            type = SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
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

    fun getStoreDetail(storeCode: String) {
        getStoreDetailJob?.cancel()
        getStoreDetailJob = getStoreDetailUseCase(storeCode).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            store = resource.data
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
                            message = resource.message,
                            type = SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun goTaskScreen(taskModel: TaskModel) {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.Navigate(
                    route = taskModel.route,
                    data = state.value.store?.code
                )
            )
        }
    }

    private fun qrScanned(storeCode: String, qr: String?) {
        viewModelScope.launch {
            if (qr != storeCode) {
                _uiEvent.send(
                    UiEvent.Navigate(
                        route = "back",
                        data = UiText.StringResorce(R.string.qr_validate_error)
                    )
                )
            } else {
                dataStoreRepository.intPutKey(AppConstants.IS_STORE_VALIDATE, 1)
                dataStoreRepository.stringPutKey(AppConstants.CURRENT_STORE_CODE, storeCode)
            }
        }
    }

    private fun nfcShouldBeOpen() {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.Navigate(
                    route = "back",
                    data = UiText.StringResorce(R.string.nfc_should_be_open)
                )
            )
        }
    }

    private fun getNFCData(nfcCode: String?, storeCode: String) {
        viewModelScope.launch {
            nfcCode?.let {
                // TODO: Chekc data and then get it
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        message = UiText.DynamicString("CODE alındı : $nfcCode"),
                        SnackBarType.SUCCESS
                    )
                )
                dataStoreRepository.intPutKey(AppConstants.IS_STORE_VALIDATE, 1)
                dataStoreRepository.stringPutKey(AppConstants.CURRENT_STORE_CODE, storeCode)
            } ?: run {
                _uiEvent.send(
                    UiEvent.Navigate(
                        route = "back",
                        data = UiText.StringResorce(R.string.nfc_error_read)
                    )
                )
            }
        }

    }


}
