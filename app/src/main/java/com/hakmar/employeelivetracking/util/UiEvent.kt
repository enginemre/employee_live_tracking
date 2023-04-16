package com.hakmar.employeelivetracking.util

sealed class UiEvent {
    data class ShowSnackBar(val message: UiText, val type: SnackBarType) : UiEvent()
    data class ScrollPosition(val index: Int) : UiEvent()
    data class FocusTextField(val type: String? = null) : UiEvent()
    data class Navigate<T>(val route: String, val data: T? = null) : UiEvent()
    data class Intent<T>(val data: T) : UiEvent()
    data class NfcReaded(val data: String) : UiEvent()
    object ShowBottomSheet : UiEvent()
    object HideKeyboard : UiEvent()
}
