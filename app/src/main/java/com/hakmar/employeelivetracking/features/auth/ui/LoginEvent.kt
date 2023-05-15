package com.hakmar.employeelivetracking.features.auth.ui

import androidx.compose.ui.focus.FocusState

sealed class LoginEvent {
    data class OnTextChange(val changedValue: String, val type: LoginFields) : LoginEvent()
    object ChangeVisibilty : LoginEvent()
    object OnLoginClick : LoginEvent()
    data class OnFocused(val focusState: FocusState, val type: LoginFields) : LoginEvent()
}

enum class LoginFields {
    PasswordText,
    UserText
}
