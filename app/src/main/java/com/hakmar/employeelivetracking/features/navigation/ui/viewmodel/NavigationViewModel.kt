package com.hakmar.employeelivetracking.features.navigation.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.domain.usecases.GetStoresUseCase
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.navigation.data.mapper.toNavigationStore
import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore
import com.hakmar.employeelivetracking.features.navigation.ui.NavigationEvent
import com.hakmar.employeelivetracking.features.navigation.ui.NavigationState
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    val dataStoreRepository: DataStoreRepository,
    val getStoresUseCase: GetStoresUseCase
) : BaseViewModel<NavigationEvent>() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var allStoreJob: Job? = null

    init {
        getAllStores()
    }

    private fun getAllStores() {
        allStoreJob?.cancel()
        allStoreJob = getStoresUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            storeList = resource.data!!.map { data -> data.toNavigationStore() }
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = resource.message,
                            type = SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getDirection(store: NavigationStore): String {
        return "http://maps.google.com/maps?daddr=${store.lat},${store.lon}"
    }

    override fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnStoreClick -> {
                _uiEvent.trySend(
                    UiEvent.Intent<String>(
                        getDirection(event.data)
                    )
                )
            }
            is NavigationEvent.GetLocaiton -> {
                getCurrentLocation(event.fusedLocationProviderClient)
            }
        }
    }

    private fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        task.result?.let {
                            dataStoreRepository.doublePutKey(
                                AppConstants.LAST_KNOWN_LOCATION_LAT,
                                task.result.latitude
                            )
                            dataStoreRepository.doublePutKey(
                                AppConstants.LAST_KNOWN_LOCATION_LON,
                                task.result.longitude
                            )
                        }
                    }
                }
                val lat = runBlocking {
                    dataStoreRepository.doubleReadKey(AppConstants.LAST_KNOWN_LOCATION_LAT)
                }
                val lon = runBlocking {
                    dataStoreRepository.doubleReadKey(AppConstants.LAST_KNOWN_LOCATION_LON)
                }
                lat?.let {
                    val latLng = LatLng(lat, lon!!)
                    _state.update {
                        it.copy(
                            lastLocation = task.result,
                            isLoading = false,
                            cameraPosition = latLng
                        )
                    }
                    viewModelScope.launch {
                        _uiEvent.send(
                            UiEvent.Navigate(
                                "",
                                latLng
                            )
                        )
                    }
                } ?: kotlin.run {
                    _state.update {
                        it.copy(
                            lastLocation = task.result,
                            isLoading = false,
                        )
                    }
                }


            }
        } catch (e: SecurityException) {
            _state.update {
                it.copy(
                    isLoading = false,
                    cameraPosition = getCameraPosition()
                )
            }
            _uiEvent.trySend(
                UiEvent.ShowSnackBar(
                    UiText.DynamicString(e.localizedMessage ?: "Konum Alınamadı"),
                    SnackBarType.WARNING
                )
            )
        }
    }

    private fun getCameraPosition(): LatLng {
        state.value.lastLocation?.let {
            return LatLng(it.latitude, it.longitude)
        } ?: kotlin.run {
            return getLastKnownLocation()
        }
    }

    private fun getLastKnownLocation(): LatLng {
        val lat = runBlocking {
            dataStoreRepository.doubleReadKey(AppConstants.LAST_KNOWN_LOCATION_LAT)
                ?: 40.99715375688897
        }
        val lon = runBlocking {
            dataStoreRepository.doubleReadKey(AppConstants.LAST_KNOWN_LOCATION_LON)
                ?: 29.233578755863043
        }
        return LatLng(lat, lon)
    }

}