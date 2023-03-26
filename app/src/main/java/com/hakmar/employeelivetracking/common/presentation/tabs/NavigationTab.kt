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
import com.hakmar.employeelivetracking.features.navigation.ui.NavigationScreen

class NavigationTab(private val innerScreens: List<Screen> = listOf(NavigationScreen())) :
    Tab {

    override val key: ScreenKey
        get() = HomeDestination.Navigation.base

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(HomeDestination.Navigation.unSelectedIcon)

            return remember {
                TabOptions(
                    index = 2u,
                    title = "PmStores",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}