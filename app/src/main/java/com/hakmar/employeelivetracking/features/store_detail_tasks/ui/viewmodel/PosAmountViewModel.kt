package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase.SendPosAmountUseCase
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.PosAmountEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.PosAmountsFields.CashierAmount
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.PosAmountsFields.Difference
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.PosAmountsFields.PosAmount
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state.PosAmountState
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PosAmountViewModel @Inject constructor(
    private val sendPosAmountUseCase: SendPosAmountUseCase
) : BaseViewModel<PosAmountEvent>() {

    private val _state = MutableStateFlow(PosAmountState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var sendAmountsJob: Job? = null

    override fun onEvent(event: PosAmountEvent) {
        when (event) {
            is PosAmountEvent.OnCompletePosAmount -> {
                sendPosAmounts(event.storeCode)
            }

            is PosAmountEvent.OnTextChange -> {
                when (event.type) {
                    PosAmount -> {
                        _state.update {
                            it.copy(
                                posAmount = event.changedValue
                            )
                        }
                    }

                    Difference -> {
                        _state.update {
                            it.copy(
                                difference = event.changedValue
                            )
                        }
                    }

                    CashierAmount -> {
                        _state.update {
                            it.copy(
                                cashierAmount = event.changedValue
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendPosAmounts(storeCode: String) {
        sendAmountsJob?.cancel()
        sendAmountsJob = sendPosAmountUseCase(
            storeCode,
            posAmount = state.value.posAmount,
            cashierAmount = state.value.cashierAmount,
            difference = state.value.difference
        ).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.successfully_steel_case_amount),
                            type = SnackBarType.SUCCESS
                        )
                    )
                    delay(250L)
                    _uiEvent.send(
                        UiEvent.Navigate<Any>(
                            route = HomeDestination.StoreDetail.base,
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


}