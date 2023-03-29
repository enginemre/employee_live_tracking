package com.hakmar.employeelivetracking.util

import com.hakmar.employeelivetracking.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException

fun formatTime(seconds: String, minutes: String, hours: String): String {
    return "$hours:$minutes:$seconds"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}

fun <T> Flow<T>.handleErrors(): Flow<Resource<T>> = flow {
    try {
        collect { value -> emit(value as Resource<T>) }
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
    } catch (e: IOException) {
        e.printStackTrace()
        emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
    } catch (e: Exception) {
        e.printStackTrace()
        emit(
            Resource.Error(
                UiText.DynamicString(
                    e.localizedMessage ?: "Unexceptred error"
                )
            )
        )
    }
}