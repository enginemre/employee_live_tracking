package com.hakmar.employeelivetracking.common.presentation

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.tabs.BsTab
import com.hakmar.employeelivetracking.common.presentation.tabs.NavigationTab
import com.hakmar.employeelivetracking.common.presentation.tabs.PmTab
import com.hakmar.employeelivetracking.common.presentation.tabs.ProfileTab
import com.hakmar.employeelivetracking.common.presentation.tabs.TaskTab
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppTopBar
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackBar
import com.hakmar.employeelivetracking.common.presentation.ui.components.DrawFloatAction
import com.hakmar.employeelivetracking.common.presentation.ui.components.HomeTopBar
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.features.notification.ui.NotificationScreen
import kotlinx.parcelize.Parcelize

@Parcelize
class HomeScreen(
    val tab: Tab = BsTab(),
) : Screen, Parcelable {

    @Composable
    override fun Content() {

        val mainViewModel = getViewModel<MainViewModel>()
        val state = mainViewModel.fabState.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        CompositionLocalProvider(
            LocalSnackbarHostState provides snackbarHostState
        ) {
            TabNavigator(tab) { navigator ->
                val parentNavigator = LocalNavigator.currentOrThrow.parent
                Scaffold(
                    topBar = {
                        DrawTopAppBar(mainViewModel = mainViewModel) {
                            parentNavigator?.push(NotificationScreen())
                        }
                    },
                    snackbarHost = {
                        CustomSnackBar(hostState = snackbarHostState)
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.White,
                            modifier = Modifier
                                .navigationBarsPadding()
                                .background(
                                    color = if (navigator.current.key == HomeDestination.BsStores.base ||
                                        navigator.current.key == HomeDestination.Profile.base ||
                                        navigator.current.key == HomeDestination.Tasks.base ||
                                        navigator.current.key == HomeDestination.Navigation.base ||
                                        navigator.current.key == HomeDestination.PmStores.base
                                    )
                                        Color.White
                                    else MaterialTheme.colors.background
                                )
                                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                                .height(80.dp)
                        ) {
                            TabNavigationItem(PmTab())
                            TabNavigationItem(TaskTab())
                            TabNavigationItem(BsTab())
                            TabNavigationItem(NavigationTab())
                            TabNavigationItem(ProfileTab())
                        }
                    },
                    floatingActionButton = {
                        if (navigator.current.key == HomeDestination.Tasks.base)
                            DrawFloatAction(icon = state.value.icon, onFabClick = {
                                state.value.onClick?.invoke()
                            })
                    },
                    content = {
                        BoxWithConstraints(
                            modifier = Modifier.padding(it),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            CurrentTab()
                        }
                    },
                )
            }
        }

    }

}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colors.primary,
            unselectedIconColor = MaterialTheme.colors.onSurface,
            indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                LocalAbsoluteTonalElevation.current
            )
        ),
        alwaysShowLabel = false,
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                tint = if (tabNavigator.current.key == tab.key) Green40 else Natural80
            )
        }
    )
}

@Composable
fun DrawTopAppBar(
    mainViewModel: MainViewModel,
    onNotificationClick: () -> Unit
) {
    val tabNavigator = LocalTabNavigator.current
    val state = mainViewModel.appBarState.collectAsState()
    val isHomeBar =
        (tabNavigator.current.key == HomeDestination.BsStores.base
                || tabNavigator.current.key == HomeDestination.PmStores.base
                || tabNavigator.current.key == HomeDestination.Navigation.base)
    if (isHomeBar)
        HomeTopBar(
            name = "Emre Engin",
            onNotificonClick = {
                onNotificationClick()
            }
        )
    else
        AppTopBar(
            title = state.value.title,
            actions = state.value.actions ?: {},
            isNavigationEnable = state.value.isNavigationButton,
            navigationClick = state.value.navigationClick ?: {}
        )
}