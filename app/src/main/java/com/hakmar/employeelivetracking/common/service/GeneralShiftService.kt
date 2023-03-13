package com.hakmar.employeelivetracking.common.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.core.app.NotificationCompat
import com.hakmar.employeelivetracking.util.AppConstants.ACTION_GENERAL_SHIFT_TIME_CANCEL
import com.hakmar.employeelivetracking.util.AppConstants.ACTION_GENERAL_SHIFT_TIME_START
import com.hakmar.employeelivetracking.util.AppConstants.ACTION_GENERAL_SHIFT_TIME_STOP
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_ID
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_NAME
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_ID
import com.hakmar.employeelivetracking.util.AppConstants.TIMER_STATE
import com.hakmar.employeelivetracking.util.TimerState
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class GeneralShiftService : Service() {

    var dataUpdateListener: GeneralShiftServiceListener? = null

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var generalShiftServiceManager: GeneralShiftServiceManager

    @Inject
    @Named("socket")
    lateinit var generalShiftSocketClient: OkHttpClient

    @Inject
    lateinit var generalShiftRequest: Request

    private var socket: WebSocket? = null


    private val binder = GeneralShiftServiceBinder()


    private var shiftSocketListener = ShiftListener()

    private fun startSocket() {
        socket = generalShiftSocketClient.newWebSocket(
            request = generalShiftRequest,
            shiftSocketListener
        )
    }


    override fun onBind(intent: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.getStringExtra(TIMER_STATE)) {
            TimerState.Started.name -> {
                startSocket()
                setStopButton()
                startForegroundService()
            }
            TimerState.Stoped.name -> {
                setResumeButton()
            }
            TimerState.Closed.name -> {
                stopForegroundService()
            }
        }
        intent?.action.let {
            when (it) {
                ACTION_GENERAL_SHIFT_TIME_START -> {
                    startSocket()
                    setStopButton()
                    startForegroundService()
                }
                ACTION_GENERAL_SHIFT_TIME_STOP -> {
                    setResumeButton()
                }
                ACTION_GENERAL_SHIFT_TIME_CANCEL -> {
                    stopForegroundService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("RestrictedApi")
    private fun setStopButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.addAction(
            NotificationCompat.Action(
                0,
                "Stop",
                generalShiftServiceManager.stopPendingIntent()
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setResumeButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.addAction(
            NotificationCompat.Action(
                0,
                "Resume",
                generalShiftServiceManager.resumePendingIntent()
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    inner class GeneralShiftServiceBinder : Binder() {
        fun getService() = this@GeneralShiftService
    }

    inner class ShiftListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            dataUpdateListener?.onConnected()
            super.onOpen(webSocket, response)
            webSocket.send("{\"message\" : \"selam\" }")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            dataUpdateListener?.onTick(text)

        }

    }

    interface GeneralShiftServiceListener {
        fun onTick(text: String)
        fun onConnected()
        fun onClose()
        fun onStop()
        fun onStart()
    }

}