package com.hakmar.employeelivetracking.common.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.util.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _appBarState = MutableStateFlow(AppBarState())
    val appBarState = _appBarState.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()


    fun loginStatus(): Int {
        val isLogin: Int =
            runBlocking { dataStoreRepository.intReadKey(AppConstants.IS_LOGIN) ?: 0 }
        savedStateHandle[AppConstants.IS_LOGIN] = isLogin
        return isLogin
    }


    fun updateAppBar(appBarState: AppBarState) {
        _appBarState.value = appBarState
    }

    fun getUserLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _userState.update {
                        it.copy(
                            lastLocation = task.result,
                        )
                    }
                }
            }
        } catch (e: SecurityException) {

        }
    }

    fun saveLastRoute(route: String) {
        runBlocking {
            dataStoreRepository.stringPutKey(AppConstants.LAST_ROUTE, route)
            dataStoreRepository.intPutKey(AppConstants.IS_ON_PAUSED, 1)
        }
    }

    fun resetLastRoute() {
        runBlocking {
            dataStoreRepository.intPutKey(AppConstants.IS_ON_PAUSED, 0)
        }
    }

    fun getLastRoute(): String? {
        val isOnPaused: Int =
            runBlocking { dataStoreRepository.intReadKey(AppConstants.IS_ON_PAUSED) ?: 0 }
        return if (isOnPaused != 0) {
            resetLastRoute()
            runBlocking {
                val route =
                    dataStoreRepository.stringReadKey(AppConstants.LAST_ROUTE)
                route
            }


        } else {
            null
        }

    }
}