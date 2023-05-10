package com.hakmar.employeelivetracking.util

import androidx.compose.ui.graphics.Color
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Warning
import com.hakmar.employeelivetracking.common.presentation.ui.theme.White
import io.jsonwebtoken.Jwts
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

fun convertStringToDuration(seconds: String, minutes: String, hours: String): Duration? {
    return if (seconds == "00" && minutes == "00" && hours == "00") {
        null
    } else {
        val second = seconds.toInt().seconds
        val minute = minutes.toInt().minutes
        val hour = hours.toInt().hours
        return second + minute + hour
    }
}

fun decodeJwt(token: String): String? {
    return try {
        val jwtParser = Jwts.parserBuilder()
            .setSigningKey("asdv234234^&%&^%&^hjsdfb2%%%anambabamburdayam".toByteArray()).build()
        val claims = jwtParser.parseClaimsJws(token).body
        val storeCode = claims["nameid"] as? String
        storeCode
    } catch (e: Exception) {
        null
    }
}

fun getContainerColor(type: SnackBarType) = when (type) {
    SnackBarType.WARNING -> Warning
    SnackBarType.SUCCESS -> Green40
    SnackBarType.ERROR -> Color.Red
}

fun getContentColor(type: SnackBarType) = when (type) {
    SnackBarType.WARNING -> White
    SnackBarType.SUCCESS -> Natural110
    SnackBarType.ERROR -> White
}

enum class SnackBarType {
    WARNING,
    SUCCESS,
    ERROR
}