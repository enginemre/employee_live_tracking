package com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel

import android.location.Location
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.service.StoreShiftServiceManager
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.CancelStoreShiftUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.GetStoreDetailUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.GetStoreShiftStatusUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.PauseStoreShiftUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.ResumeStoreShiftUseCase
import com.hakmar.employeelivetracking.features.store_detail.domain.usecase.StartStoreShiftUseCase
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailEvent
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailState
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.AppConstants.GO_BACK
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.TimerState
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import com.hakmar.employeelivetracking.util.await
import com.hakmar.employeelivetracking.util.convertStringToDuration
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StoreDetailScreenModel @AssistedInject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val pauseStoreShiftUseCase: PauseStoreShiftUseCase,
    private val startStoreShiftUseCase: StartStoreShiftUseCase,
    private val cancelStoreShiftUseCase: CancelStoreShiftUseCase,
    private val resumeStoreShiftUseCase: ResumeStoreShiftUseCase,
    private val getStoreDetailUseCase: GetStoreDetailUseCase,
    private val initStoreShiftStatusUseCase: GetStoreShiftStatusUseCase,
    private val storeShiftServiceManager: StoreShiftServiceManager,
    @Assisted private val storeCode: String
) : ScreenModel {

    @AssistedFactory
    interface Factory : ScreenModelFactory {
        fun create(storeCode: String): StoreDetailScreenModel
    }

    init {

        initStatus(storeCode)
        getStoreDetail(storeCode)
    }

    private var _state = MutableStateFlow(StoreDetailState())
    val state = _state.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private var startStoreShiftJob: Job? = null
    private var resumeStoreShiftJob: Job? = null
    private var pauseStoreShiftJob: Job? = null
    private var cancelStoreShiftJob: Job? = null
    private var getStoreDetailJob: Job? = null
    private var getStoreStatusJob: Job? = null


    fun onEvent(event: StoreDetailEvent) {
        when (event) {
            is StoreDetailEvent.OnTaskClick -> {
                goTaskScreen(event.taskModel)
            }

            is StoreDetailEvent.OnTick -> {
                onTick(event.hour, event.minute, event.second)
            }

            is StoreDetailEvent.OnActionButtonClick -> {
                coroutineScope.launch {
                    val result = getCurrentLocation(event.fusedLocationProviderClient)
                    result?.let { loc ->
                        actionButtonClick(event.storeCode, loc.latitude, loc.longitude)
                    } ?: kotlin.run {
                        actionButtonClick(event.storeCode, 0.0, 0.0)
                    }
                }
            }

            is StoreDetailEvent.OnStopButtonClick -> {
                coroutineScope.launch {
                    val result = getCurrentLocation(event.fusedLocationProviderClient)
                    result?.let { loc ->
                        stopButtonClick(loc.latitude, loc.longitude)
                    } ?: kotlin.run {
                        stopButtonClick(0.0, 0.0)
                    }
                }
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
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        seconds = timerStatus.timer.second,
                                        hours = timerStatus.timer.hour,
                                        minutes = timerStatus.timer.minute,
                                        isPlaying = TimerState.Started,
                                    )
                                }
                                startStoreShiftService()
                            }

                            "paused" -> {
                                _state.update {
                                    it.copy(
                                        isPlaying = TimerState.Stoped,
                                        seconds = timerStatus.timer.second,
                                        minutes = timerStatus.timer.minute,
                                        hours = timerStatus.timer.hour,
                                    )
                                }
                            }

                            "closed" -> {
                                _state.update {
                                    it.copy(
                                        isPlaying = TimerState.Closed
                                    )
                                }
                            }

                            else -> {}
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
        }.launchIn(coroutineScope)
    }


    private fun actionButtonClick(storeCode: String, lat: Double, lon: Double) {
        when (state.value.isPlaying) {
            TimerState.Idle -> {
                start(storeCode, lat, lon)
            }

            TimerState.Stoped -> {
                resume(storeCode)
            }

            TimerState.Started -> {
                pause(storeCode)
            }

            TimerState.Closed -> {}
        }
    }

    private fun resume(storeCode: String) {
        resumeStoreShiftJob?.cancel()
        resumeStoreShiftJob = resumeStoreShiftUseCase(storeCode).onEach { resource ->
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
                }
            }
        }.launchIn(coroutineScope)
    }

    private fun start(storeCode: String, lat: Double, lon: Double) {
        startStoreShiftJob?.cancel()
        startStoreShiftJob = startStoreShiftUseCase(storeCode, lat, lon).onEach { resource ->
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
        }.launchIn(coroutineScope)
    }

    private fun pause(storeCode: String) {
        pauseStoreShiftService(storeCode = storeCode)
    }

    private fun stopButtonClick(lat: Double, lon: Double) {
        cancelStoreShiftJob?.cancel()
        cancelStoreShiftJob = cancelStoreShiftUseCase(storeCode, lat, lon).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    stopStoreShiftService()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            seconds = "00",
                            minutes = "00",
                            hours = "00",
                            initialTime = 1,
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.store_shift_successfully_completed),
                            type = SnackBarType.SUCCESS
                        )
                    )
                    delay(100)
                    _uiEvent.send(
                        UiEvent.Navigate(
                            data = null,
                            route = GO_BACK
                        )
                    )
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
        }.launchIn(coroutineScope)

    }

    private fun startStoreShiftService() {
        val pausedTime =
            convertStringToDuration(state.value.seconds, state.value.minutes, state.value.hours)
        if (state.value.isPlaying == TimerState.Idle) {
            pausedTime?.let {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START,
                    it.toString(),
                    storeCode
                )
            } ?: kotlin.run {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START,
                    storeCode = storeCode
                )
            }
        } else {
            pausedTime?.let {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START,
                    it.toString(),
                    storeCode
                )
            } ?: kotlin.run {
                storeShiftServiceManager.triggerForegroundService(
                    AppConstants.ACTION_STORE_SHIFT_TIME_START,
                    storeCode = storeCode
                )
            }
        }
    }

    private fun stopStoreShiftService() {
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

    private fun pauseStoreShiftService(storeCode: String) {
        pauseStoreShiftJob?.cancel()
        pauseStoreShiftJob = pauseStoreShiftUseCase(storeCode).onEach { resource ->
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
        }.launchIn(coroutineScope)
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


    private fun getStoreDetail(storeCode: String) {
        getStoreDetailJob?.cancel()
        getStoreDetailJob = getStoreDetailUseCase(storeCode).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { store ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                store = resource.data
                            )
                        }
                        if (!store.isStoreShiftEnable) {
                            _uiEvent.send(
                                UiEvent.ShowSnackBar(
                                    UiText.StringResorce(R.string.error_shift_completed),
                                    SnackBarType.ERROR
                                )
                            )
                            delay(200)
                            _uiEvent.send(
                                UiEvent.Navigate(
                                    route = GO_BACK,
                                    data = null
                                )
                            )
                        }
                    }
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
        }.launchIn(coroutineScope)
    }

    private fun goTaskScreen(taskModel: TaskModel) {
        coroutineScope.launch {
            _uiEvent.send(
                UiEvent.Navigate(
                    route = taskModel.route,
                    data = state.value.store?.code
                )
            )
        }
    }

    private suspend fun getCurrentLocation(fusedLocationProviderClient: FusedLocationProviderClient): Location? {
        return try {
            val locationResult = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                }).await()
            locationResult
        } catch (e: SecurityException) {
            e.printStackTrace()
            null
        }
    }

}