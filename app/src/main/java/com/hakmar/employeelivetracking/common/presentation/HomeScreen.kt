package com.hakmar.employeelivetracking.common.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hakmar.employeelivetracking.common.presentation.base.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeNavGraph
import com.hakmar.employeelivetracking.common.presentation.graphs.screens
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppTopBar
import com.hakmar.employeelivetracking.common.presentation.ui.components.HomeTopBar
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    windowSizeClass: WindowSizeClass,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        topBar = {
            DrawTopBar(currentDestination = currentDestination, mainViewModel = mainViewModel)
        },
        bottomBar = { BottomBar(navController = navController) },
    ) {
        HomeNavGraph(navController = navController, mainViewModel = mainViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    val bottomBarDestination = screens.any { it.path == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier
                .navigationBarsPadding()
                .clip(navShape)
                .height(80.dp)
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun DrawTopBar(currentDestination: NavDestination?, mainViewModel: MainViewModel) {
    val state = mainViewModel.appBarState.collectAsState()
    val isHomeBar = currentDestination?.hierarchy?.any { mainRoute ->
        screens.any { mainRoute.route == it.base }
    } == true
    if (isHomeBar)
        HomeTopBar()
    else
        AppTopBar(
            title = state.value.title,
            actions = state.value.actions ?: {},
            isNavigationEnable = state.value.isNavigationButton,
            navigationClick = state.value.navigationClick ?: {}
        )
}

@Composable
fun RowScope.AddItem(
    screen: HomeDestination,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colors.primary,
            unselectedIconColor = MaterialTheme.colors.onSurface,
            indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                LocalAbsoluteTonalElevation.current
            )
        ),
        icon = {
            Icon(
                imageVector = if (currentDestination?.hierarchy?.any {
                        it.route == screen.path
                    } == true) screen.selectedIcon else screen.unSelectedIcon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.path
        } == true,
        alwaysShowLabel = false,
        onClick = {
            navController.navigate(screen.path) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}