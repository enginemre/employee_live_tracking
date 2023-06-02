package com.hakmar.employeelivetracking.common.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.hakmar.employeelivetracking.MainActivity
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.graphs.DeepLinkRouter
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.util.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StoreShiftServiceManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) {
    private val flag =
        PendingIntent.FLAG_IMMUTABLE

    fun clickPendingIntent(): PendingIntent {
        val storeCode =
            runBlocking { dataStoreRepository.stringReadKey(AppConstants.CURRENT_STORE_CODE) }
        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            "${DeepLinkRouter.InsideAppUri}${HomeDestination.StoreDetail.base}/$storeCode".toUri(),
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(AppConstants.STORE_SHIFT_CLICK_REQUEST_CODE, flag)!!
        }
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
}