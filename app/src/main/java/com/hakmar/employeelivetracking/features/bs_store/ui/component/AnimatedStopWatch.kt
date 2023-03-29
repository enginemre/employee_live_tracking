package com.hakmar.employeelivetracking.features.bs_store.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment

@Composable
fun AnimatedStopWatch(
    hour: String,
    minitue: String,
    second: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
            CounterText(hour.toInt())
            Text(text = ":")
            CounterText(minitue.toInt())
            Text(text = ":")
            CounterText(second.toInt())
        }
    }
}