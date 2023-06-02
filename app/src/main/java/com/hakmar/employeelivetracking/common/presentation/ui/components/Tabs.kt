package com.hakmar.employeelivetracking.common.presentation.ui.components

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Tab.TabContent(
    vararg startScreen: Screen
) {
    val tabTitle = options.title

    LifecycleEffect(
        onStarted = { Log.d("Navigator", "Start tab $tabTitle") },
        onDisposed = { Log.d("Navigator", "Dispose tab $tabTitle") },
    )

    Navigator(screens = startScreen.toList()) { navigator ->
        SlideTransition(navigator) { screen ->
            Column {
                screen.Content()
                Log.d("Navigator", "Last Event: ${navigator.lastEvent}")
            }
        }
    }
}