package com.hakmar.employeelivetracking.features.profile.ui.events


sealed class EditProfileEvent {
    data class OnTextChange(val changedValue: String, val type: EditProfileFields) :
        EditProfileEvent()

    object OnSaveClick : EditProfileEvent()
    data class ChangeVisibilty(val type: EditProfileFields) : EditProfileEvent()
}


enum class EditProfileFields {
    Email,
    NewPassword,
    OldPasswod
}