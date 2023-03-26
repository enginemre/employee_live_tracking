@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.hakmar.employeelivetracking

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.hakmar.employeelivetracking.common.presentation.DeepLink
import com.hakmar.employeelivetracking.common.presentation.DeepLinkController
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.service.GeneralShiftService
import com.hakmar.employeelivetracking.common.service.StoreShiftService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val deepLinkNavigation: MutableState<DeepLink> = mutableStateOf(DeepLink())
    private var isBoundGeneral by mutableStateOf(false)
    private var generalShiftService: GeneralShiftService? = null
    private var storeShiftService: StoreShiftService? = null
    private val storeShiftConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as StoreShiftService.StoreShiftServiceBinder
            storeShiftService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }
    private val generalShiftConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as GeneralShiftService.GeneralShiftServiceBinder
            generalShiftService = binder.getService()
            isBoundGeneral = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBoundGeneral = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmployeeLiveTrackingTheme {
                RootScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, GeneralShiftService::class.java).also {
            bindService(it, generalShiftConnection, BIND_AUTO_CREATE)
        }
        Intent(this, StoreShiftService::class.java).also {
            bindService(it, storeShiftConnection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(generalShiftConnection)
        unbindService(storeShiftConnection)
        isBoundGeneral = false
    }

    @Composable
    fun RootScreen() {
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
        LaunchedEffect(key1 = Unit ){
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        val isLogin = mainViewModel.loginStatus()
        val startDestination =
            if (isLogin == 1) Destination.Home.base else Destination.Auth.base
        val snackbarHostState = remember { SnackbarHostState() }
        DeepLinkController.HandleDeepLink(
            deepLinkNavigation = deepLinkNavigation.value,
            startDestination = startDestination,
            snackbarHostState = snackbarHostState
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleIntent(intent, null)
    }
    private fun handleIntent(intent: Intent?, savedInstanceState: Bundle?) {
        if(intent?.action == Intent.ACTION_VIEW){
            val uri = intent.data
            val list = uri.toString().split("/")
            val data = list[list.lastIndex]
            val route = list[list.lastIndex-1]
            this.deepLinkNavigation.value = DeepLink(route = route,data, isEmpty = false)
        }
    }
}