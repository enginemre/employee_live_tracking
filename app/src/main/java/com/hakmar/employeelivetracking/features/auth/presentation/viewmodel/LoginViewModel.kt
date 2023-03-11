package com.hakmar.employeelivetracking.features.auth.presentation.viewmodel

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.Destination
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.auth.domain.usecase.AuthUseCases
import com.hakmar.employeelivetracking.features.auth.domain.usecase.UserValidateUseCase
import com.hakmar.employeelivetracking.features.auth.presentation.LoginEvent
import com.hakmar.employeelivetracking.features.auth.presentation.LoginFields
import com.hakmar.employeelivetracking.features.auth.presentation.LoginState
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel<LoginEvent>() {

    private var _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var loginJob: Job? = null

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnFocused -> {
                onFocused(event.focusState, type = event.type)
            }
            is LoginEvent.OnLoginClick -> {
                validateLogin()
            }
            is LoginEvent.OnTextChange -> {
                if (event.type == LoginFields.UserText)
                    _state.update {
                        it.copy(
                            usercode = event.changedValue
                        )
                    }
                else
                    _state.update {
                        it.copy(
                            password = event.changedValue
                        )
                    }
            }
        }
    }

    private fun onLogin(userCode: String, password: String) {
        loginJob?.cancel()
        loginJob = authUseCases.loginUseCase(userCode, password).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    savedStateHandle[AppConstants.IS_LOGIN] = 1
                    _uiEvent.send(
                        UiEvent.Navigate(
                            Destination.Home.base
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
                            message = resource.message
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateLogin() {
        when (val validate =
            authUseCases.userValidateUseCase(state.value.usercode, state.value.password)) {
            is UserValidateUseCase.LoginValidationResult.Error -> {
                _uiEvent.trySend(
                    UiEvent.ShowSnackBar(
                        validate.message
                    )
                )
            }
            is UserValidateUseCase.LoginValidationResult.Success -> {
                onLogin(validate.userCode, validate.password)
            }
        }
    }

    private fun onFocused(focusState: FocusState, type: LoginFields) {
        if (type == LoginFields.UserText) {
            _state.update {
                it.copy(
                    isActiveUser = when {
                        focusState.isFocused -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                )
            }
        } else {
            _state.update {
                it.copy(
                    isActivePassword = when {
                        focusState.isFocused -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                )
            }
        }

    }
}