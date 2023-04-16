package com.hakmar.employeelivetracking.common.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.TimerState
import com.hakmar.employeelivetracking.util.formatTime
import com.hakmar.employeelivetracking.util.pad
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class StoreShiftService : Service() {

    var isServiceRunning: Boolean = false

    var dataUpdateListener: StoreShiftServiceListener? = null

    private val binder = StoreShiftServiceBinder()

    override fun onBind(intent: Intent?) = binder

    var seconds = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("00")
        private set
    var hours = mutableStateOf("00")
        private set
    var currentState = mutableStateOf(TimerState.Idle)
        private set
    var storeCode = mutableStateOf("****")

    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var exitStoreShiftJob: Job? = null

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    @Named("store")
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var storeShiftServiceManager: StoreShiftServiceManager

    private var tickIntent = Intent(
        AppConstants.ACTION_OBSERVE_STORE_SHIFT
    )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isServiceRunning = true
        var lastTime: Duration? = null
        intent?.getStringExtra(AppConstants.LAST_TIME)?.let {
            lastTime = Duration.parse(it)
        }
        intent?.action.let {
            when (it) {
                AppConstants.ACTION_STORE_SHIFT_TIME_START -> {
                    if (currentState.value != TimerState.Started) {
                        startForegroundService(
                            intent?.getStringExtra(AppConstants.STORE_INFO) ?: "****"
                        )
                        startTimer(pausedTime = lastTime,
                            onTick = { hours, minutes, seconds ->
                                updateNotification(
                                    hours = hours,
                                    minutes = minutes,
                                    seconds = seconds
                                )
                                dataUpdateListener?.onTick(hours, minutes, seconds)
                                tickIntent.putExtra(
                                    AppConstants.TIME_ELAPSED,
                                    formatTime(seconds, minutes, hours)
                                )
                                sendBroadcast(tickIntent)
                            }
                        )
                    }
                }
                AppConstants.ACTION_STORE_SHIFT_TIME_STOP -> {
                    stopTimer()
                }
                AppConstants.ACTION_STORE_SHIFT_TIME_CANCEL -> {
                    stopTimer()
                    stopForegroundService()
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun createNotificationChannel(storeCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AppConstants.NOTIFICATION_CHANNEL_ID_STORE_SHIFT,
                AppConstants.NOTIFICATION_CHANNEL_NAME_STORE,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
            notificationBuilder.setContentTitle(storeCode + "  mesai devam ediyor")
        }
    }

    private fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            AppConstants.NOTIFICATION_ID_STORE,
            notificationBuilder.setContentText(
                formatTime(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }

    private fun updateTimeState() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@StoreShiftService.hours.value = hours.toInt().pad()
            this@StoreShiftService.minutes.value = minutes.pad()
            this@StoreShiftService.seconds.value = seconds.pad()
        }

    }

    private fun startForegroundService(storeCode: String) {
        createNotificationChannel(storeCode)
        startForeground(AppConstants.NOTIFICATION_ID_STORE, notificationBuilder.build())
    }

    private fun stopForegroundService() {
        notificationManager.cancel(AppConstants.NOTIFICATION_ID_STORE)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
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

    private fun exitService() {
        stopTimer()
        stopForegroundService()
        /* runBlocking {
            dataStoreRepository.intPutKey(AppConstants.IS_STORE_VALIDATE, 1)
            dataStoreRepository.stringPutKey(AppConstants.CURRENT_STORE_CODE, storeCode.value)
        }*/
    }


    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exitService()
    }


    inner class StoreShiftServiceBinder : Binder() {
        fun getService() = this@StoreShiftService
    }

    interface StoreShiftServiceListener {
        fun onTick(h: String, m: String, s: String)
    }
}