@file:OptIn(ExperimentalAnimationApi::class)

package com.hakmar.employeelivetracking.features.bs_store.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment

@Composable
fun StopWatchClock(
    hour: String,
    minitue: String,
    second: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val numberTransition: AnimatedContentScope<String>.() -> ContentTransform = {
            slideInVertically(initialOffsetY = { it }) + fadeIn() with slideOutVertically(
                targetOffsetY = { -it }) + fadeOut() using SizeTransform(true)
        }
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
            AnimatedContent(targetState = hour, transitionSpec = numberTransition) {
                Text(text = hour)
            }
            Text(text = ":")
            AnimatedContent(targetState = minitue, transitionSpec = numberTransition) {
                Text(text = minitue)
            }
            Text(text = ":")
            AnimatedContent(targetState = second, transitionSpec = numberTransition) {
                Text(text = second)
            }

        }
    }
}