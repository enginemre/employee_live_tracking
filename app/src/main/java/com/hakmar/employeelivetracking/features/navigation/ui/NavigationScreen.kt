 package com.hakmar.employeelivetracking.features.navigation.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackbarVisuals
import com.hakmar.employeelivetracking.common.presentation.ui.components.LoadingDialog
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.navigation.ui.component.NavigationStoreCard
import com.hakmar.employeelivetracking.features.navigation.ui.viewmodel.NavigationViewModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor

 class NavigationScreen : Screen {

    override val key: ScreenKey
        get() = HomeDestination.Navigation.base

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel = getViewModel<NavigationViewModel>()
        val mainViewModel = getViewModel<MainViewModel>()
        val mapLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
        val state = viewModel.state.collectAsStateWithLifecycle()
        val mapProp = MapProperties(isMyLocationEnabled = state.value.lastLocation != null)
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val snackbarhost = LocalSnackbarHostState.current
        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(state.value.cameraPosition, 15f)
        }
        LaunchedEffect(key1 = Unit) {
            if (mainViewModel.isGranetedLocaitonPermission)
                viewModel.onEvent(NavigationEvent.GetLocaiton(fusedLocationProviderClient))
        }
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Intent<*> -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(event.data as String)
                        )
                        mapLauncher.launch(intent)
                    }
                    is UiEvent.ShowSnackBar -> {
                        snackbarhost.showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.message.asString(context),
                                containerColor = getContainerColor(event.type),
                                contentColor = getContentColor(event.type)
                            )
                        )
                    }
                    is UiEvent.Navigate<*> -> {
                        cameraPosition.position =
                            CameraPosition.fromLatLngZoom(event.data as LatLng, 15f)
                    }
                    else -> {}
                }
            }
        }
        if (state.value.isLoading)
            LoadingDialog(stateLoading = state.value.isLoading)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {
            item(key = "2") {
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
                    state.value.storeList.forEach {
                        Marker(
                            state = MarkerState(position = LatLng(it.lat, it.lon)),
                            title = it.storeName,
                            snippet = it.storeCode
                        )
                    }
                }
            }
            items(state.value.storeList) {
                Box(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
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