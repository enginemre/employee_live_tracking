package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase.GetStoreOutsideUseCase
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase.SendStoreOutSideUseCase
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.StoreOutsideEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state.StoreOutsideState
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
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

class StoreOutsideScreenModel @AssistedInject constructor(
    private val getStoreOutsideUseCase: GetStoreOutsideUseCase,
    private val sendStoreOutSideUseCase: SendStoreOutSideUseCase,
    @Assisted private val storeCode: String,
) : ScreenModel {

    @AssistedFactory
    interface Factory : ScreenModelFactory {
        fun create(storeCode: String): StoreOutsideScreenModel
    }

    private val _state = MutableStateFlow(StoreOutsideState())
    val state = _state.asStateFlow()

    private val _checkList = mutableStateListOf<CheckItem>()
    val checkList: List<CheckItem> = _checkList

    private var getCheckListJob: Job? = null
    private var sendiCheckListJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getStoreOutsideList()
    }


    fun onEvent(event: StoreOutsideEvent) {
        when (event) {
            StoreOutsideEvent.CompleteCheck -> {
                completeCheck()
            }

            is StoreOutsideEvent.OnChecked -> {
                changeStatus(event.checkItem)
            }
        }
    }

    private fun changeStatus(checkItem: CheckItem) {
        val copyList = checkList.toMutableList()
        val index = copyList.indexOf(checkItem)
        copyList[index] = copyList[index].copy(
            completed = !checkItem.completed
        )
        _checkList.clear()
        _checkList.addAll(copyList)
    }

    private fun completeCheck() {
        if (checkList.none { it.completed }) {
            coroutineScope.launch {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        message = UiText.StringResorce(R.string.error_can_not_send_without_modify),
                        type = SnackBarType.ERROR
                    )
                )
            }
        } else {
            sendStoreOutsideList(storeCode)
        }
    }

    private fun sendStoreOutsideList(storeCode: String) {
        sendiCheckListJob?.cancel()
        sendiCheckListJob = sendStoreOutSideUseCase(
            storeCode,
            checkList.filter { it.completed }).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.successfully_sent),
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
        }.launchIn(coroutineScope)
    }

    private fun getStoreOutsideList() {
        getCheckListJob?.cancel()
        getCheckListJob = getStoreOutsideUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        _checkList.addAll(it)
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            headers = resource.data?.groupBy { gruop -> gruop.type }?.keys?.toList()
                                ?: emptyList()
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
        }.launchIn(coroutineScope)
    }
}