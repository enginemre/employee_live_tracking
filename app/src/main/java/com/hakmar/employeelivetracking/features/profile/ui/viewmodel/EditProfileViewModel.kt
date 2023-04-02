package com.hakmar.employeelivetracking.features.profile.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.profile.domain.usecase.UpdateUserInfoUseCase
import com.hakmar.employeelivetracking.features.profile.ui.events.EditProfileEvent
import com.hakmar.employeelivetracking.features.profile.ui.events.EditProfileFields
import com.hakmar.employeelivetracking.features.profile.ui.states.EditProfileState
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : BaseViewModel<EditProfileEvent>() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var updateUserInfoJob: Job? = null


    override fun onEvent(event: EditProfileEvent) {
        when (event) {
            EditProfileEvent.OnSaveClick -> {
                updateUserInfo()
            }
            is EditProfileEvent.OnTextChange -> {
                when (event.type) {
                    EditProfileFields.Email -> _state.update {
                        it.copy(
                            email = event.changedValue
                        )
                    }
                    EditProfileFields.NewPassword -> {
                        _state.update {
                            it.copy(
                                newPassword = event.changedValue
                            )
                        }
                    }
                    else -> _state.update {
                        it.copy(
                            oldPassword = event.changedValue
                        )
                    }
                }
            }
            is EditProfileEvent.ChangeVisibilty -> {
                if (event.type == EditProfileFields.OldPasswod)
                    _state.update {
                        it.copy(
                            isVisibleOldPassword = !state.value.isVisibleOldPassword
                        )
                    }
                else
                    _state.update {
                        it.copy(
                            isVisibleNewPassword = !state.value.isVisibleNewPassword
                        )
                    }
            }
        }
    }

    private fun updateUserInfo() {
        updateUserInfoJob?.cancel()
        updateUserInfoJob = updateUserInfoUseCase(
            state.value.oldPassword,
            state.value.newPassword,
            state.value.email
        ).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            oldPassword = "",
                            newPassword = "",
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResorce(R.string.update_info_success),
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
                            SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}