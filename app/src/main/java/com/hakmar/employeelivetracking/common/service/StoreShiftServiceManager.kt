package com.hakmar.employeelivetracking.common.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.hakmar.employeelivetracking.MainActivity
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.TimerState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreShiftServiceManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val flag =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE
        else
            0

    fun clickPendingIntent(): PendingIntent {
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(AppConstants.TIMER_STATE, TimerState.Started.name)
        }
        return PendingIntent.getActivity(
            context, AppConstants.STORE_SHIFT_CLICK_REQUEST_CODE, clickIntent, flag
        )
    }

    fun triggerForegroundService(
        action: String,
        timerData: String? = null,
        storeCode: String? = "****"
    ) {
        Intent(context, StoreShiftService::class.java).apply {
            putExtra(AppConstants.LAST_TIME, timerData)
            putExtra(AppConstants.STORE_INFO, storeCode)
            this.action = action
            context.startService(this)
        }
    }

    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, StoreShiftService::class.java).apply {
            putExtra(AppConstants.TIMER_STATE, TimerState.Closed.name)
        }
        return PendingIntent.getService(
            context, AppConstants.STORE_SHIFT_CANCEL_REQUEST_CODE, cancelIntent, flag
        )
    }
}