package com.hakmar.employeelivetracking.features.navigation.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore
import com.hakmar.employeelivetracking.features.navigation.ui.NavigationEvent
import com.hakmar.employeelivetracking.features.navigation.ui.NavigationState
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    val dataStoreRepository: DataStoreRepository,
) : BaseViewModel<NavigationEvent>() {

    private val _state = MutableStateFlow(NavigationState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun getDirection(store: NavigationStore): String {
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
        }
    }

    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
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
                    _state.update {
                        it.copy(
                            lastLocation = task.result
                        )
                    }
                }
            }
        } catch (e: SecurityException) {
            _uiEvent.trySend(
                UiEvent.ShowSnackBar(
                    UiText.DynamicString(e.localizedMessage ?: "Konum Alınamadı")
                )
            )
        }
    }

    fun getCameraPosition(): LatLng {
        state.value.lastLocation?.let {
            return LatLng(it.latitude, it.longitude)
        } ?: kotlin.run {
            return getLastKnownLocation()
        }
    }

    fun getLastKnownLocation(): LatLng {
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