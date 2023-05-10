package com.hakmar.employeelivetracking.common.presentation.ui.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

@Composable
fun SystemReciver(action : String, onSystemEvent: (intent: Intent?) -> Unit) {

    val context = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(newValue = onSystemEvent)
  
    DisposableEffect(context, action){

        val intentFilter = IntentFilter(action)
        val broadcastReceiver = object  : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                currentOnSystemEvent(intent)
            }
        }
        context.registerReceiver(broadcastReceiver,intentFilter)
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}