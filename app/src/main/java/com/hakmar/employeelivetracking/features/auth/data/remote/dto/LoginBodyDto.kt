package com.hakmar.employeelivetracking.features.auth.data.remote.dto


data class LoginBodyDto(
    val password: String,
    val username: String
)