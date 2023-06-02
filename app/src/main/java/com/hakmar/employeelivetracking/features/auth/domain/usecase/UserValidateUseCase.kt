package com.hakmar.employeelivetracking.features.auth.domain.usecase

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.util.UiText

class UserValidateUseCase {
    operator fun invoke(userCode: String, password: String): LoginValidationResult {
        if (userCode.isEmpty()) {
            return LoginValidationResult.Error(message = UiText.StringResorce(R.string.user_code_empty))
        }
        if (password.isEmpty()) {
            return LoginValidationResult.Error(message = UiText.StringResorce(R.string.password_empty))
        }
        return LoginValidationResult.Success(userCode = userCode, password = password)
    }

    sealed class LoginValidationResult {
        data class Success(val userCode: String, val password: String) : LoginValidationResult()
        data class Error(val message: UiText) : LoginValidationResult()
    }
}