package com.hakmar.employeelivetracking.features.pm_store.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.pm_store.domain.usecase.GetPmStoresUseCase
import com.hakmar.employeelivetracking.features.pm_store.ui.PmStoreEvent
import com.hakmar.employeelivetracking.features.pm_store.ui.PmStoreState
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PmStoreViewModel @Inject constructor(
    private val getPmStoresUseCase: GetPmStoresUseCase
) : BaseViewModel<PmStoreEvent>() {

    private var _state = MutableStateFlow(PmStoreState())
    val state = _state.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var pmStores: Job? = null

    init {
        getPmStores()
    }

    override fun onEvent(event: PmStoreEvent) {
        when (event) {
            is PmStoreEvent.OnStoreClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.error_authorization),
                            type = SnackBarType.WARNING
                        )
                    )
                }
            }

        }
    }

    private fun getPmStores() {
        pmStores?.cancel()
        pmStores = getPmStoresUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            pmStoresList = resource.data!!
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
        }.launchIn(viewModelScope)
    }

}