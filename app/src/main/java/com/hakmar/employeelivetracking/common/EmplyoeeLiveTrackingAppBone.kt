package com.hakmar.employeelivetracking.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hakmar.employeelivetracking.common.presentation.HomeScreen
import com.hakmar.employeelivetracking.common.presentation.graphs.authNavGraph
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackBar
import com.hakmar.employeelivetracking.common.service.GeneralShiftService


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmployeeLiveTrackingAppBone(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    startDestination: String,
    bounded: Boolean,
    generalShiftService: GeneralShiftService?,
) {
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            CustomSnackBar(hostState = snackbarHostState)
        }
    ) {
        NavHost(
            navController = navController,
            route = Destination.Root.base,
            startDestination = startDestination
        ) {
            authNavGraph(
                navController = navController,
                windowSizeClass = windowSizeClass,
                snackbarHostState = snackbarHostState
            )
            composable(route = Destination.Home.base) {
                if (bounded)
                    HomeScreen(
                        windowSizeClass = windowSizeClass,
                        generalShiftService = generalShiftService
                    )
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


