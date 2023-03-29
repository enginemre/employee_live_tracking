package com.hakmar.employeelivetracking.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.features.auth.ui.LoginScreen
import com.hakmar.employeelivetracking.features.onboarding.ui.OnBoardingScreen

object DeepLinkController {

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun HandleDeepLink(
        deepLinkNavigation: DeepLink,
        startDestination: String,
    ) {
        val screens = mutableListOf<Screen>()
        screens.clear()
        if (!deepLinkNavigation.isEmpty) {
            screens.addAll(parseNavigation(deepLinkNavigation))
        } else {
            when (startDestination) {
                Destination.Auth.base -> {
                    screens.add(
                        LoginScreen()
                    )
                }
                Destination.Home.base -> {
                    screens.add(
                        HomeScreen()
                    )
                }
                Destination.OnBoarding.base -> {
                    screens.add(
                        OnBoardingScreen()
                    )
                }
            }

        }
        Navigator(screens = screens) {
            SlideTransition(it) { screen ->
                screen.Content()
            }

        }

    }

    private fun parseNavigation(deepLinkNavigation: DeepLink): List<Screen> {

        return emptyList()
    }
}

