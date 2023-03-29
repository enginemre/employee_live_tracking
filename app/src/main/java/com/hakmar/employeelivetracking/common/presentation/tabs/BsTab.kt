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
import com.hakmar.employeelivetracking.features.bs_store.ui.BsStoreScreen

class BsTab(private val innerScreens: List<Screen> = listOf(BsStoreScreen())) : Tab {

    override val key: ScreenKey
        get() = HomeDestination.BsStores.base
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(HomeDestination.BsStores.unSelectedIcon)
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Bölge Sorumluları",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}