package com.hakmar.employeelivetracking.common.presentation.graphs

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hakmar.employeelivetracking.common.presentation.HomeScreen
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmployeeLiveTrackingApp(navController: NavHostController, windowSizeClass: WindowSizeClass) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            CustomSnackBar(hostState = snackbarHostState)
        }
    ) {
        NavHost(
            navController = navController,
            route = Destination.Root.base,
            startDestination = Destination.Auth.base
        ) {
            authNavGraph(
                navController = navController,
                windowSizeClass = windowSizeClass,
                snackbarHostState = snackbarHostState
            )
            composable(route = Destination.Home.base) {
                HomeScreen(windowSizeClass = windowSizeClass)
            }
        }
    }

}


sealed interface Destination {
    val base: String
    val path: String

    object Root : Destination {
        override val base: String = "/"
        override val path: String = base
    }

    object Auth : Destination {
        override val base: String = "/auth"
        override val path: String = base
    }

    object Home : Destination {
        override val base: String = "/home"
        override val path: String = base
    }
}


