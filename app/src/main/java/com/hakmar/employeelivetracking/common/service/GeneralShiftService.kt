package com.hakmar.employeelivetracking.common.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.AppConstants.ACTION_GENERAL_SHIFT_TIME_CANCEL
import com.hakmar.employeelivetracking.util.AppConstants.ACTION_GENERAL_SHIFT_TIME_START
import com.hakmar.employeelivetracking.util.AppConstants.ACTION_GENERAL_SHIFT_TIME_STOP
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_ID_GENERAL_SHIFT
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_NAME_GENERAL
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_ID_GENERAL
import com.hakmar.employeelivetracking.util.TimerState
import com.hakmar.employeelivetracking.util.formatTime
import com.hakmar.employeelivetracking.util.pad
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class GeneralShiftService : Service() {

    var isServiceRunning: Boolean = false

    var dataUpdateListener: GeneralShiftServiceListener? = null

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var generalShiftServiceManager: GeneralShiftServiceManager


    var seconds = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("00")
        private set
    var hours = mutableStateOf("00")
        private set
    var currentState = mutableStateOf(TimerState.Idle)
        private set


    private val binder = GeneralShiftServiceBinder()

    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer

    private var tickIntent = Intent(
        AppConstants.ACTION_OBSERVE_GENERAL_SHIFT
    )


    override fun onBind(intent: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isServiceRunning = true
        var lastTime: Duration? = null
        intent?.getStringExtra(AppConstants.LAST_TIME)?.let {
            lastTime = Duration.parse(it)
        }
        intent?.action.let {
            when (it) {
                ACTION_GENERAL_SHIFT_TIME_START -> {
                    startForegroundService()
                    startTimer(pausedTime = lastTime,
                        onTick = { hours, minutes, seconds ->
                            updateNotification(hours = hours, minutes = minutes, seconds = seconds)
                            dataUpdateListener?.onTick(hours, minutes, seconds)
                            tickIntent.putExtra(AppConstants.TIME_ELAPSED, formatTime(seconds, minutes, hours))
                            sendBroadcast(tickIntent)
                        }
                    )
                }
                ACTION_GENERAL_SHIFT_TIME_STOP -> {
                    stopTimer()
                }
                ACTION_GENERAL_SHIFT_TIME_CANCEL -> {
                    stopForegroundService()
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID_GENERAL_SHIFT,
                NOTIFICATION_CHANNEL_NAME_GENERAL,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID_GENERAL, notificationBuilder.build())
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID_GENERAL)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    inner class GeneralShiftServiceBinder : Binder() {
        fun getService() = this@GeneralShiftService
    }

    private fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID_GENERAL,
            notificationBuilder.setContentText(
                formatTime(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }

    private fun startTimer(
        pausedTime: Duration? = null,
        onTick: (h: String, m: String, s: String) -> Unit
    ) {
        currentState.value = TimerState.Started
        time = pausedTime ?: Duration.ZERO
        pausedTime?.let {
            updateTimeState()
        }
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(1.seconds)
            updateTimeState()

            onTick(hours.value, minutes.value, seconds.value)
        }
    }

    private fun stopTimer() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        currentState.value = TimerState.Stoped
    }

    private fun updateTimeState() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@GeneralShiftService.hours.value = hours.toInt().pad()
            this@GeneralShiftService.minutes.value = minutes.pad()
            this@GeneralShiftService.seconds.value = seconds.pad()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
    }


    interface GeneralShiftServiceListener {
        fun onTick(h: String, m: String, s: String)
    }

}