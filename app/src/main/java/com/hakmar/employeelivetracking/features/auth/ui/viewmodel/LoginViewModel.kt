package com.hakmar.employeelivetracking.features.auth.ui.viewmodel

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.domain.repository.CommonRepository
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.features.auth.domain.usecase.AuthUseCases
import com.hakmar.employeelivetracking.features.auth.domain.usecase.UserValidateUseCase
import com.hakmar.employeelivetracking.features.auth.presentation.LoginEvent
import com.hakmar.employeelivetracking.features.auth.presentation.LoginFields
import com.hakmar.employeelivetracking.features.auth.presentation.ui.LoginState
import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val commonRepository: CommonRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val savedStateHandle: SavedStateHandle
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
        val token = runBlocking { dataStoreRepository.stringReadKey(AppConstants.FIREBASE_TOKEN) }
        token?.let {
            commonRepository.sendFCMToken(token).launchIn(viewModelScope)
        }
        loginJob?.cancel()
        loginJob = authUseCases.loginUseCase(userCode, password).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _uiEvent.send(
                        UiEvent.Intent(User(nameSurname = resource.data?.nameSurname ?: "", ""))
                    )
                    savedStateHandle[AppConstants.IS_LOGIN] = 1
                    _uiEvent.send(
                        UiEvent.Navigate(
                            Destination.Home.base,
                            data = null
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

    private fun validateLogin() {
        when (val validate =
            authUseCases.userValidateUseCase(state.value.usercode, state.value.password)) {
            is UserValidateUseCase.LoginValidationResult.Error -> {
                _uiEvent.trySend(
                    UiEvent.ShowSnackBar(
                        validate.message,
                        SnackBarType.WARNING
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