package com.hakmar.employeelivetracking.features.profile.ui.states

data class EditProfileState(
    var isLoading: Boolean = false,
    var newPassword: String = "",
    var oldPassword: String = "",
    var isVisibleOldPassword: Boolean = false,
    var isVisibleNewPassword: Boolean = false,
    var email: String = ""
)
