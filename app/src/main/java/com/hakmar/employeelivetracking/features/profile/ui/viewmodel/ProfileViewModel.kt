package com.hakmar.employeelivetracking.features.profile.ui.viewmodel

import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.features.profile.ui.events.ProfileEvent
import com.hakmar.employeelivetracking.features.profile.ui.states.ProfilState
import com.hakmar.employeelivetracking.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(

) : BaseViewModel<ProfileEvent>() {

    private val _state = MutableStateFlow(ProfilState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
}