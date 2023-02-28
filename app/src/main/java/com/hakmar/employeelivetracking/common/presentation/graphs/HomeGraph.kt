package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hakmar.employeelivetracking.common.presentation.BsStoresScreen
import com.hakmar.employeelivetracking.common.presentation.PMStoreScreen
import com.hakmar.employeelivetracking.util.BottomBarScreen
import com.hakmar.employeelivetracking.util.Route

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Route.HOME,
        startDestination = BottomBarScreen.BsStores.route
    ) {
        composable(route = BottomBarScreen.BsStores.route) {
            BsStoresScreen()
        }
        composable(route = BottomBarScreen.PmStores.route) {
            PMStoreScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {

        }

    }
}