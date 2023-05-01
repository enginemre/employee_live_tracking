package com.hakmar.employeelivetracking.common.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hakmar.employeelivetracking.MainActivity
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.domain.usecases.SendFCMTokenUseCase
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_ID_APP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class EmployeeFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var sendFCMTokenUseCase: SendFCMTokenUseCase

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var jobSendToken: Job? = null
    private var jobSaveToken: Job? = null

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
//        val link =  message.notification?.clickAction
        val link = message.data["link"]
        message.notification?.let {
            generateNotification(it.title ?: "Mesaj örneği", it.body ?: "Messjjj", link)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        jobSendToken?.cancel()
        jobSendToken = sendFCMTokenUseCase(token).launchIn(serviceScope)
        runBlocking {
            dataStoreRepository.stringPutKey(AppConstants.FIREBASE_TOKEN, token)
        }
    }

    private fun generateNotification(title: String, message: String, link: String?) {
        createNotificationChannel()
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        link?.let { destination ->
            if (destination.isNotEmpty()) {
                val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID_APP)
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    destination.toUri(),
                    this,
                    MainActivity::class.java
                )
                val pendingIntent = TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(intent)
                    getPendingIntent(
                        AppConstants.STORE_SHIFT_CLICK_REQUEST_CODE,
                        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                    )!!
                }
                notification
                    .setSmallIcon(R.drawable.notification)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setChannelId(NOTIFICATION_CHANNEL_ID_APP)
                    .setSound(alarmSound)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .priority = NotificationManager.IMPORTANCE_MAX

                notificationManager.notify(
                    AppConstants.NOTIFICATION_ID_APP,
                    notification.build()
                )
            } else {
                val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID_APP)
                val intent = Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                val pendingIntent =
                    PendingIntent.getActivity(
                        this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                    )
                notification
                    .setSmallIcon(R.drawable.notification)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setChannelId(NOTIFICATION_CHANNEL_ID_APP)
                    .setSound(alarmSound)
                    .priority = NotificationManager.IMPORTANCE_MAX

                notificationManager.notify(
                    AppConstants.NOTIFICATION_ID_APP,
                    notification.build()
                )
            }
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID_APP,
            AppConstants.NOTIFICATION_CHANNEL_NAME_APP,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        notificationManager.createNotificationChannel(channel)
    }
}