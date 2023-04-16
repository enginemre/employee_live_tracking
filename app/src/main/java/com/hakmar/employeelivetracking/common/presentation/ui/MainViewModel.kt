package com.hakmar.employeelivetracking.common.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.FabState
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<MainEvent>() {

    private val _appBarState = MutableStateFlow(AppBarState())
    val appBarState = _appBarState.asStateFlow()

    private val _fabState = MutableStateFlow(FabState(icon = Icons.Default.Add))
    val fabState = _fabState.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var exitShiftJob: Job? = null
    private var exitStoreShiftJob: Job? = null


    override fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.OnNfcDataNotRead -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            type = SnackBarType.ERROR,
                            message = UiText.StringResorce(R.string.nfc_error_read)
                        )
                    )
                }
            }

            MainEvent.OnNfcNotOpened -> {
                nfcShouldBeOpen()
            }

            is MainEvent.OnNfcRead -> {
                getNFCData(event.data, event.storeCode)
            }

            is MainEvent.OnQRScan -> {
                qrScanned(event.storeCode, event.data)
            }

            MainEvent.OnQrDataNotRead -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            type = SnackBarType.ERROR,
                            message = UiText.StringResorce(R.string.qr_error_read)
                        )
                    )
                }
            }
        }
    }

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

    fun logOut() {
        viewModelScope.launch {
            dataStoreRepository.intPutKey(AppConstants.IS_LOGIN, 0)
            dataStoreRepository.clearDataStore(AppConstants.USER_ID)
        }
    }

    fun isValidatedBefore(storeCode: String): Boolean {
        val storedStore =
            runBlocking { dataStoreRepository.stringReadKey(AppConstants.CURRENT_STORE_CODE) }
        val isValidated =
            runBlocking { dataStoreRepository.intReadKey(AppConstants.IS_STORE_VALIDATE) }
        return storeCode == storedStore && isValidated == 1
    }

    private fun getNFCData(nfcCode: String?, storeCode: String) {
        viewModelScope.launch {
            nfcCode?.let {
                // TODO: Chekc data and then get it
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        message = UiText.DynamicString("CODE alındı : $nfcCode"),
                        SnackBarType.SUCCESS
                    )
                )
                dataStoreRepository.intPutKey(AppConstants.IS_STORE_VALIDATE, 1)
                dataStoreRepository.stringPutKey(AppConstants.CURRENT_STORE_CODE, storeCode)
            } ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        type = SnackBarType.ERROR,
                        message = UiText.StringResorce(R.string.nfc_error_read)
                    )
                )
            }
        }
    }

    private fun qrScanned(storeCode: String, qr: String?) {
        viewModelScope.launch {
            if (qr != storeCode) {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        type = SnackBarType.ERROR,
                        message = UiText.StringResorce(R.string.qr_validate_error)
                    )
                )
            } else {
                dataStoreRepository.intPutKey(AppConstants.IS_STORE_VALIDATE, 1)
                dataStoreRepository.stringPutKey(AppConstants.CURRENT_STORE_CODE, storeCode)
                _uiEvent.send(
                    UiEvent.Navigate(
                        route = HomeDestination.StoreDetail.base,
                        data = storeCode
                    )
                )
            }
        }
    }

    private fun nfcShouldBeOpen() {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowSnackBar(
                    message = UiText.StringResorce(R.string.nfc_should_be_open),
                    type = SnackBarType.ERROR
                )
            )
        }
    }


}