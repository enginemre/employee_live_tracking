package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hakmar.employeelivetracking.common.presentation.HomeScreen
import com.hakmar.employeelivetracking.util.Route


@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Route.ROOT,
        startDestination = Route.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Route.HOME) {
            HomeScreen()
        }
    }
}
