package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hakmar.employeelivetracking.auth.presentation.ui.LoginScreen
import com.hakmar.employeelivetracking.util.Route

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Route.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen()
        }
        composable(route = AuthScreen.Verification.route) {

        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "/login")
    object Verification : AuthScreen(route = "/verification")
}
