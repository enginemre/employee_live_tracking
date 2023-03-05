package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hakmar.employeelivetracking.features.auth.presentation.ui.LoginScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    snackbarHostState: SnackbarHostState
) {
    val router = Router(navController)
    navigation(
        route = Destination.Auth.base,
        startDestination = AuthDestination.Login.base
    ) {
        composable(route = AuthDestination.Login.base) {
            LoginScreen(windowSizeClass = windowSizeClass, snackbarHostState = snackbarHostState) {
                router.goHome()
            }
        }
        composable(route = AuthDestination.Verification.base) {

        }
    }
}

private class Router(val navController: NavController) {
    fun goHome() {
        navController.popBackStack()
        navController.navigate(Destination.Home.base)
    }

}

private sealed interface AuthDestination {
    val base: String
    val path: String

    object Login : Destination {
        override val base: String = "/login"
        override val path: String = base
    }

    object Verification : Destination {
        override val base: String = "/verification"
        override val path: String = base
    }
}