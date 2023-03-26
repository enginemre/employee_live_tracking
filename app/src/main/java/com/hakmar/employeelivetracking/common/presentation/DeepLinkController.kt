package com.hakmar.employeelivetracking.common.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.features.auth.presentation.ui.LoginScreen

object DeepLinkController {

    @Composable
    fun HandleDeepLink(
        deepLinkNavigation: DeepLink,
        startDestination: String,
        snackbarHostState: SnackbarHostState,
    ) {
            val screens = mutableListOf<Screen>()
            screens.clear()
            if (!deepLinkNavigation.isEmpty) {
                screens.addAll(parseNavigation(deepLinkNavigation))
            } else {
                when (startDestination) {
                    Destination.Auth.base -> {
                        screens.add(
                            LoginScreen(snackbarHostState)
                        )
                    }
                    Destination.Home.base -> {
                        screens.add(
                            HomeScreen()
                        )
                    }
                }

            }
            Navigator(screens = screens) {
                CurrentScreen()
            }

    }

    private fun parseNavigation(deepLinkNavigation: DeepLink): List<Screen> {

        return emptyList()
    }
}

