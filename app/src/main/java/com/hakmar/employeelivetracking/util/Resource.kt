package com.hakmar.employeelivetracking.util

sealed class Resource<T>(
    val data: T? = null,
    val message: UiText = UiText.DynamicString("Beklenmedik Bir Hata Oluştu")
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: UiText, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

