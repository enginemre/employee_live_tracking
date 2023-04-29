package com.hakmar.employeelivetracking.features.pm_store.ui

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackbarVisuals
import com.hakmar.employeelivetracking.common.presentation.ui.components.DevicePreviews
import com.hakmar.employeelivetracking.common.presentation.ui.components.LoadingDialog
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.pm_store.ui.component.PmStoreCard
import com.hakmar.employeelivetracking.features.pm_store.ui.model.PmStoreModel
import com.hakmar.employeelivetracking.features.pm_store.ui.viewmodel.PmStoreViewModel
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailScreen
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor


@ExperimentalGetImage
class PMStoreScreen : Screen {

    override val key: ScreenKey
        get() = HomeDestination.PmStores.base


    @Composable
    override fun Content() {
        val viewModel = getViewModel<PmStoreViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val mainViewModel = getViewModel<MainViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        if (state.isLoading)
            LoadingDialog(stateLoading = state.isLoading)
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.message.asString(context),
                                contentColor = getContentColor(event.type),
                                containerColor = getContainerColor(event.type)
                            )
                        )
                    }

                    is UiEvent.Navigate<*> -> {
                        when (event.route) {
                            HomeDestination.StoreDetail.base -> {
                                navigator.push(StoreDetailScreen(event.data as String))
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
        LaunchedEffect(key1 = Unit) {
            mainViewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.message.asString(context),
                                contentColor = getContentColor(event.type),
                                containerColor = getContainerColor(event.type)
                            )
                        )
                    }

                    is UiEvent.Navigate<*> -> {
                        when (event.route) {
                            HomeDestination.StoreDetail.base -> {
                                navigator.push(StoreDetailScreen(event.data as String))
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.large),
                columns = GridCells.Adaptive(150.dp),
                content = {
                    items(state.pmStoresList) { item ->
                        Box(
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.small)
                        ) {
                            PmStoreCard(item.code, item.name) {
                                viewModel.onEvent(PmStoreEvent.OnStoreClick(item))
                            }
                        }

                    }
                }
            )
        }
    }

}


@DevicePreviews
@Composable
fun PmScreenPrev() {
    val list = listOf<PmStoreModel>(
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
    )
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.large),
                content = {
                    items(list) { item ->
                        Box(
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.small)
                        ) {
                            PmStoreCard(item.storeCode, item.storeName) {}
                        }

                    }
                }
            )
        }
    }
}