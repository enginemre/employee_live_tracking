package com.hakmar.employeelivetracking.features.profile.ui.viewmodel

import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.profile.ui.events.EditProfileEvent
import com.hakmar.employeelivetracking.features.profile.ui.events.EditProfileFields
import com.hakmar.employeelivetracking.features.profile.ui.states.EditProfileState
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

) : BaseViewModel<EditProfileEvent>() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    override fun onEvent(event: EditProfileEvent) {
        when (event) {
            EditProfileEvent.OnSaveClick -> {

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
}