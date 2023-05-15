package com.hakmar.employeelivetracking.features.auth.ui

data class LoginState(
    var usercode: String = "",
    var password: String = "",
    var isActiveUser: Boolean = false,
    var isActivePassword: Boolean = false,
    var isLoading: Boolean = false,
    var isVisiblePassword: Boolean = false,
)
