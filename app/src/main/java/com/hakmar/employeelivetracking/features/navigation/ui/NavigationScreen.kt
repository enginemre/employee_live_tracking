package com.hakmar.employeelivetracking.features.navigation.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.navigation.ui.component.NavigationStoreCard
import com.hakmar.employeelivetracking.features.navigation.ui.viewmodel.NavigationViewModel
import com.hakmar.employeelivetracking.util.UiEvent


@Composable
fun NavigationScreen(
    mainViewModel: MainViewModel,
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val mapLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
    val state = viewModel.state.collectAsState()

    val mapProp = MapProperties(
        isMyLocationEnabled = state.value.lastLocation != null,
    )

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.getCameraPosition(), 15f)
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Intent<*> -> {
                    mainViewModel.saveLastRoute(HomeDestination.Navigation.base)
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(event.data as String)
                    )
                    mapLauncher.launch(intent)
                }
                else -> Unit
            }
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.getCurrentLocation(fusedLocationProviderClient)
    }
    Column {
        GoogleMap(
            properties = mapProp,
            modifier = Modifier.height(250.dp),
            cameraPositionState = cameraPosition
        ) {
            state.value.lastLocation?.let {
                Circle(
                    center = LatLng(it.latitude, it.longitude),
                    clickable = false,
                    radius = 200.0,
                    strokeColor = MaterialTheme.colors.primary,
                    fillColor = MaterialTheme.colors.primary.copy(alpha = 0.6f)
                )
            }

            state.value.storeList?.forEach {
                Marker(
                    state = MarkerState(position = LatLng(it.lat, it.lon)),
                    title = it.storeName,
                    snippet = it.storeCode
                )
            }


        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            items(state.value.storeList!!) {
                NavigationStoreCard(
                    storeCode = it.storeCode,
                    storeName = it.storeName
                ) {
                    viewModel.onEvent(NavigationEvent.OnStoreClick(it))
                }
            }
        }


    }

}


@Preview(showSystemUi = true)
@Composable
fun NavigationScreenPrev() {
    EmployeeLiveTrackingTheme {
        val myHome = LatLng(40.895034, 29.241965)
        val marker = MarkerState(myHome)
        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(myHome, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            Marker(
                state = marker,
                title = "Evim"
            )
        }
    }
}