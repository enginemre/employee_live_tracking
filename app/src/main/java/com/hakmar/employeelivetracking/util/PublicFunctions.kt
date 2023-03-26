package com.hakmar.employeelivetracking.util

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.auth0.android.jwt.JWT
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {
    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

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
    val jwt = JWT(token)
    val user = jwt.getClaim("user").asString()
    val uuui = jwt.getClaim("user_uuid").asString()
    val storeCode = jwt.getClaim("nameid").asString()
    val isExpired = jwt.isExpired(3)
    return if (!isExpired) user else null
}