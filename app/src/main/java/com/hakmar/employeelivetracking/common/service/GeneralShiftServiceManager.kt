package com.hakmar.employeelivetracking.common.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hakmar.employeelivetracking.MainActivity
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.AppConstants.GENERAL_SHIFT_CLICK_REQUEST_CODE
import com.hakmar.employeelivetracking.util.TimerState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GeneralShiftServiceManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val flag =
        PendingIntent.FLAG_IMMUTABLE

    fun clickPendingIntent(): PendingIntent {
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(AppConstants.TIMER_STATE, TimerState.Started.name)
        }
        return PendingIntent.getActivity(
            context, GENERAL_SHIFT_CLICK_REQUEST_CODE, clickIntent, flag
        )
    }

    fun triggerForegroundService(action: String, timerData: String? = null) {
        Intent(context, GeneralShiftService::class.java).apply {
            putExtra(AppConstants.LAST_TIME, timerData)
            this.action = action
            context.startService(this)
        }
    }
}