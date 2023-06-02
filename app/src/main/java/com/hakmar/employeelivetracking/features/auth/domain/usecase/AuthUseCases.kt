package com.hakmar.employeelivetracking.features.auth.domain.usecase

data class AuthUseCases(
    val loginUseCase: LoginUseCase,
    val userValidateUseCase: UserValidateUseCase
)
