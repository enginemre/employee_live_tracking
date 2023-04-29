package com.hakmar.employeelivetracking.common.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.common.presentation.tabs.BsTab
import com.hakmar.employeelivetracking.common.presentation.tabs.TaskTab
import com.hakmar.employeelivetracking.features.auth.ui.LoginScreen
import com.hakmar.employeelivetracking.features.bs_store.ui.BsStoreScreen
import com.hakmar.employeelivetracking.features.onboarding.ui.OnBoardingScreen
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailScreen
import com.hakmar.employeelivetracking.features.tasks.ui.screen.TaskDetailScreen
import com.hakmar.employeelivetracking.features.tasks.ui.screen.TasksScreen

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

    @SuppressLint("UnsafeOptInUsageError")
    private fun parseNavigation(deepLinkNavigation: DeepLink): List<Screen> {
        when (deepLinkNavigation.route) {
            "store" -> {
                return if (deepLinkNavigation.data.isNotEmpty()) {
                    listOf(
                        HomeScreen(
                            BsTab(
                                listOf(
                                    BsStoreScreen(),
                                    StoreDetailScreen(deepLinkNavigation.data)
                                )
                            )
                        )
                    )
                } else {
                    listOf(HomeScreen())
                }
            }

            "task_detail" -> {
                return if (deepLinkNavigation.data.isNotEmpty() && deepLinkNavigation.data.toIntOrNull() != null) {
                    listOf(
                        HomeScreen(
                            TaskTab(
                                listOf(
                                    TasksScreen(),
                                    TaskDetailScreen(deepLinkNavigation.data.toInt())
                                )
                            )
                        )
                    )
                } else {
                    listOf(HomeScreen())
                }
            }

            else -> {
                return emptyList()
            }
        }
    }
}

