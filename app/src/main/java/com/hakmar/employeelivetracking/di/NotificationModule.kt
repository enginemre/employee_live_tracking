package com.hakmar.employeelivetracking.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.service.GeneralShiftServiceManager
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
        generalShiftServiceManager: GeneralShiftServiceManager
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Deneme")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.back)
            .setOngoing(true)
            .addAction(0, "Stop", generalShiftServiceManager.stopPendingIntent())
            .addAction(0, "Cancel", generalShiftServiceManager.cancelPendingIntent())
            .setContentIntent(generalShiftServiceManager.clickPendingIntent())
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}