package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase.SendSteelCaseUseCase
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField.Banknotes
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField.Coins
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField.Difference
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField.SteelCaseAmount
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField.TotalPayments
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField.TotalPos
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state.SteelCaseAmountState
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SteelCaseAmountViewModel @Inject constructor(
    private val sendSteelCaseUseCase: SendSteelCaseUseCase
) : BaseViewModel<SteelCaseAmountEvent>() {


    private val _state = MutableStateFlow(SteelCaseAmountState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var sendSteelCaseAmountJob: Job? = null
    override fun onEvent(event: SteelCaseAmountEvent) {
        when (event) {
            is SteelCaseAmountEvent.OnCompleteSteelCaseAmount -> {
                onComplete(event.storeCode)
            }

            is SteelCaseAmountEvent.OnTextChange -> {
                when (event.type) {
                    Banknotes -> {
                        _state.update {
                            it.copy(
                                banknotes = event.changedValue
                            )
                        }
                    }

                    Coins -> {
                        _state.update {
                            it.copy(
                                coins = event.changedValue
                            )
                        }

                    }

                    TotalPos -> {
                        _state.update {
                            it.copy(
                                totalPosAmount = event.changedValue
                            )
                        }
                    }

                    TotalPayments -> {
                        _state.update {
                            it.copy(
                                totalPayment = event.changedValue
                            )
                        }
                    }

                    SteelCaseAmount -> {
                        _state.update {
                            it.copy(
                                steelCaseAmount = event.changedValue
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
                }
            }
        }
    }

    private fun onComplete(storeCode: String) {
        sendSteelCaseAmountJob?.cancel()
        sendSteelCaseAmountJob = sendSteelCaseUseCase(
            SendSteelCaseUseCase.Params(
                storeCode = storeCode,
                cashTotal = state.value.banknotes,
                coinTotal = state.value.coins,
                posAmountTotal = state.value.totalPosAmount,
                safeCurrentTotal = state.value.steelCaseAmount,
                difference = state.value.difference
            )
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