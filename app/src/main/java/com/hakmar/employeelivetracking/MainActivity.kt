@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.hakmar.employeelivetracking

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.hakmar.employeelivetracking.common.Destination
import com.hakmar.employeelivetracking.common.EmployeeLiveTrackingAppBone
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.service.GeneralShiftService
import com.hakmar.employeelivetracking.common.service.StoreShiftService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()


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
        val isLogin = mainViewModel.loginStatus()
        val startDestination =
            if (isLogin == 1) Destination.Home.base else Destination.Auth.base
        setContent {
            EmployeeLiveTrackingTheme {
                EmployeeLiveTrackingAppBone(
                    navController = rememberNavController(),
                    startDestination = startDestination,
                    windowSizeClass = calculateWindowSizeClass(activity = this),
                    bounded = isBoundGeneral,
                    generalShiftService = generalShiftService,
                    storeShiftService = storeShiftService,
                )
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
}