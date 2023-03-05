package com.hakmar.employeelivetracking.features.auth.presentation

data class LoginState(
    var usercode: String = "",
    var password: String = "",
    var isActiveUser: Boolean = false,
    var isActivePassword: Boolean = false,
    var isLoading: Boolean = false
)
