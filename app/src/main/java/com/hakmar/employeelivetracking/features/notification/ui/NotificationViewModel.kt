package com.hakmar.employeelivetracking.features.notification.ui

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.notification.domain.usecase.GetNotificationsUseCase
import com.hakmar.employeelivetracking.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : BaseViewModel<NotificationEvent>() {

    private val _state = MutableStateFlow(NotificationState())
    val state = _state.asStateFlow()


    private var notificationJob: Job? = null

    init {
        getNotification()
    }

    private fun getNotification() {
        notificationJob?.cancel()
        notificationJob = getNotificationsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            notifications = resource.data!!
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

    private fun getNotificationWithoutLoading() {
        notificationJob?.cancel()
        notificationJob = getNotificationsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            notifications = resource.data!!
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isRefreshing = true
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: NotificationEvent) {
        when (event) {
            NotificationEvent.OnRefreshNotification -> {
                getNotificationWithoutLoading()
            }
        }
    }
}