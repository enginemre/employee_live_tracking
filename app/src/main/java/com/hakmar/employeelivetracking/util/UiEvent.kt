package com.hakmar.employeelivetracking.util

sealed class UiEvent {
    data class ShowSnackBar(val message: UiText) : UiEvent()
    data class ScrollPosition(val index: Int) : UiEvent()
    data class FocusTextField(val type: String? = null) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object ShowBottomSheet : UiEvent()
    object HideKeyboard : UiEvent()
}
