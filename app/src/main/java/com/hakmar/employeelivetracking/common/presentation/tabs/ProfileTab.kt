package com.hakmar.employeelivetracking.common.presentation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.components.TabContent
import com.hakmar.employeelivetracking.features.profile.ui.screen.ProfileScreen

class ProfileTab(private val innerScreens: List<Screen> = listOf(ProfileScreen())) : Tab {

    override val key: ScreenKey
        get() = HomeDestination.Profile.base

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(HomeDestination.Profile.unSelectedIcon)

            return remember {
                TabOptions(
                    index = 2u,
                    title = "Profile",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}