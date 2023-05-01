package com.hakmar.employeelivetracking.common.presentation.ui

import android.location.Location

data class UserState(
    var lastLocation: Location? = null,
    var nameSurname: String = ""
)
