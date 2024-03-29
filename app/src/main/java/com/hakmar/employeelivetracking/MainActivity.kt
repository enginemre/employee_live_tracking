package com.hakmar.employeelivetracking

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.location.LocationServices
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.DeepLink
import com.hakmar.employeelivetracking.common.presentation.DeepLinkController
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.service.GeneralShiftService
import com.hakmar.employeelivetracking.common.service.StoreShiftService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

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
        try {
            val client = LocationServices.getFusedLocationProviderClient(this)
            mainViewModel.getUserLocation(client)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        handleIntent(intent = intent)
        val isLogin = mainViewModel.loginStatus()
        val isFirst = mainViewModel.isFirst()
        val startDestination =
            if (isLogin == 1)
                Destination.Home.base
            else
                if (isFirst == 0)
                    Destination.OnBoarding.base
                else
                    Destination.Auth.base
        DeepLinkController.HandleDeepLink(
            deepLinkNavigation = deepLinkNavigation.value,
            startDestination = startDestination,
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            val uri = intent.data
            val list = uri.toString().split("/")
            val data = list[list.lastIndex]
            val route = list[list.lastIndex - 1]
            this.deepLinkNavigation.value = DeepLink(route = route, data, isEmpty = false)
        }
    }
}