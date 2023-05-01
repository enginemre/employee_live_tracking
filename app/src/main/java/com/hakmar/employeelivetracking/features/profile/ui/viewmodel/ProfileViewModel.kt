package com.hakmar.employeelivetracking.features.profile.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.features.profile.domain.usecase.GetUserInfoUseCase
import com.hakmar.employeelivetracking.features.profile.ui.events.ProfileEvent
import com.hakmar.employeelivetracking.features.profile.ui.states.ProfilState
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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<ProfileEvent>() {

    private val _state = MutableStateFlow(ProfilState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var userInfoJob: Job? = null

    init {
        getUserInfo()
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLogOut -> {

            }
            is ProfileEvent.OnProfileItemClick<*> -> {
                when (event.destination as String) {
                    HomeDestination.Notification.base -> {
                        _uiEvent.trySend(
                            UiEvent.Navigate(
                                data = Any(),
                                route = event.destination
                            )
                        )
                    }
                    ProfileDestination.EditProfile.base -> {
                        _uiEvent.trySend(
                            UiEvent.Navigate(
                                data = Any(),
                                route = event.destination
                            )
                        )
                    }
                    ProfileDestination.PrivatePolicy.base -> {
                        _uiEvent.trySend(
                            UiEvent.Navigate(
                                data = Any(),
                                route = event.destination
                            )
                        )
                    }
                    ProfileDestination.AboutUs.base -> {
                        _uiEvent.trySend(
                            UiEvent.Navigate(
                                data = Any(),
                                route = event.destination
                            )
                        )
                    }
                    ProfileDestination.Logout.base -> {
                        _uiEvent.trySend(
                            UiEvent.Navigate(
                                data = Any(),
                                route = event.destination
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getUserInfo() {
        userInfoJob?.cancel()
        userInfoJob = getUserInfoUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            nameSurname = resource.data?.nameSurname,
                            email = resource.data?.email
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
}