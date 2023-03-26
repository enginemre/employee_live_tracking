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
import com.hakmar.employeelivetracking.features.pm_store.ui.PMStoreScreen

class PmTab(private val innerScreens: List<Screen> = listOf(PMStoreScreen())) : Tab {

    override val key: ScreenKey
        get() = HomeDestination.PmStores.base

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(HomeDestination.PmStores.unSelectedIcon)

            return remember {
                TabOptions(
                    index = 2u,
                    title = "Pm Screen",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}