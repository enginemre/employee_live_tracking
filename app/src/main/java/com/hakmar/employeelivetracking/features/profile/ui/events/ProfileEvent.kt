package com.hakmar.employeelivetracking.features.profile.ui.events

sealed class ProfileEvent {
    object OnLogOut : ProfileEvent()
    data class OnProfileItemClick<T>(val destination: T) : ProfileEvent()
}
