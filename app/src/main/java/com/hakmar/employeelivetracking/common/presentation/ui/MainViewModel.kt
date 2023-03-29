package com.hakmar.employeelivetracking.common.presentation.ui

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.FabState
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.StopGeneralShiftUseCase
import com.hakmar.employeelivetracking.util.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val stopGeneralShiftUseCase: StopGeneralShiftUseCase,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _appBarState = MutableStateFlow(AppBarState())
    val appBarState = _appBarState.asStateFlow()

    private val _fabState = MutableStateFlow(FabState(icon = Icons.Default.Add))
    val fabState = _fabState.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private var exitShiftJob: Job? = null


    fun loginStatus(): Int {
        val isLogin: Int =
            runBlocking { dataStoreRepository.intReadKey(AppConstants.IS_LOGIN) ?: 0 }
        savedStateHandle[AppConstants.IS_LOGIN] = isLogin
        return isLogin
    }


    fun updateAppBar(appBarState: AppBarState) {
        _appBarState.value = appBarState
    }

    fun updateFabState(fabState: FabState) {
        _fabState.value = fabState
    }

    fun isFirst(): Int {
        return runBlocking { dataStoreRepository.intReadKey(AppConstants.IS_FIRST) ?: 0 }
    }

    fun setIsFirst() {
        runBlocking {
            dataStoreRepository.intPutKey(AppConstants.IS_FIRST, 1)
        }
    }

    fun exitShiftService() {
        Log.d(AppConstants.DEBUG_TAG,"Exiting Request")
        exitShiftJob?.cancel()
        exitShiftJob = stopGeneralShiftUseCase(type = StopGeneralShiftUseCase.PauseType.Exit)
            .launchIn(viewModelScope)
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
}