package com.hakmar.employeelivetracking.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.service.GeneralShiftServiceManager
import com.hakmar.employeelivetracking.common.service.StoreShiftServiceManager
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_ID_GENERAL_SHIFT
import com.hakmar.employeelivetracking.util.AppConstants.NOTIFICATION_CHANNEL_ID_STORE_SHIFT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
        generalShiftServiceManager: GeneralShiftServiceManager
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_GENERAL_SHIFT)
            .setContentTitle("Genel mesainiz devam ediyor")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.notification)
            .setOngoing(true)
            .setContentIntent(generalShiftServiceManager.clickPendingIntent())
    }

    @ServiceScoped
    @Named("store")
    @Provides
    fun provideStoreNotificationBuilder(
        @ApplicationContext context: Context,
        storeShiftServiceManager: StoreShiftServiceManager
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_STORE_SHIFT)
            .setSmallIcon(R.drawable.shop)
            .setOngoing(true)
            .setContentIntent(storeShiftServiceManager.clickPendingIntent())
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}