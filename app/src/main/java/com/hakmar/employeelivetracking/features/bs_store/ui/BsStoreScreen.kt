package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.components.*
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.bs_store.ui.component.CircleIndicator
import com.hakmar.employeelivetracking.features.bs_store.ui.component.StoreCardItem
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailScreen
import com.hakmar.employeelivetracking.util.*

class BsStoreScreen : Screen {


    @Composable
    override fun Content() {
        val viewModel = getViewModel<BsStoreViewModel>()
        val state = viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        SystemReciver(action = AppConstants.ACTION_OBSERVE_GENERAL_SHIFT) {
            if (it?.action == AppConstants.ACTION_OBSERVE_GENERAL_SHIFT) {
                val string = it.getStringExtra(AppConstants.TIME_ELAPSED)
                val result = string!!.split(":")
                viewModel.onEvent(BsStoreEvent.OnTick(result[0], result[1], result[2]))
            }
        }
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
        if (state.value.isLoading) {
            LoadingDialog(stateLoading = state.value.isLoading)
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.small
            )
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircleIndicator(
                        indicatorValue = state.value.initialTime,
                        maxIndicatorValue = state.value.maxValueOfTime,
                        backgroundIndicatorStrokeWidth = 75f,
                        foregroundIndicatorStrokeWidth = 75f,
                        hour = state.value.hours,
                        minitue = state.value.minutes,
                        second = state.value.seconds
                    )
                    LargeButton(
                        text = stringResource(id = state.value.buttonText),
                        onClick = { viewModel.onEvent(BsStoreEvent.OnGeneralShiftClick) },
                        containerColor = state.value.containerColor,
                        textColor = state.value.buttonTextColor
                    )
                }
            }
            state.value.storeList?.let {
                storeList(it, navigator = navigator)
            }

        }
    }


}

@DevicePreviews
@Composable
fun BsStoreScreenPrev() {
    val list = listOf(
        Store(
            name = "Fatih Esenyalı",
            code = "5004",
            passedTime = "2 saat 12 dk",
            taskCount = 25,
            completedTask = 25,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
        Store(
            name = "Güzelyalı",
            code = "5024",
            passedTime = "1 saat 12 dk",
            taskCount = 25,
            completedTask = 12,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
        Store(
            name = "Gözdağı Pendik",
            code = "5054",
            passedTime = "12 dk",
            taskCount = 25,
            completedTask = 3,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
        Store(
            name = "Gülsuyu Maltepe",
            code = "5014",
            passedTime = "2 saat 12 dk",
            taskCount = 25,
            completedTask = 17,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
        Store(
            name = "Güzelyalı",
            code = "5024",
            passedTime = "1 saat 12 dk",
            taskCount = 25,
            completedTask = 12,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
        Store(
            name = "Gözdağı Pendik",
            code = "5054",
            passedTime = "12 dk",
            taskCount = 25,
            completedTask = 3,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
        Store(
            name = "Gülsuyu Maltepe",
            code = "5014",
            passedTime = "2 saat 12 dk",
            taskCount = 25,
            completedTask = 17,
            id = "",
            lattitude = 23.3,
            longtitude = 43.5,
            areaCode = "001"
        ),
    )
    EmployeeLiveTrackingTheme {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircleIndicator(
                        indicatorValue = 15,
                        maxIndicatorValue = 35,
                        foregroundIndicatorStrokeWidth = 75f,
                        backgroundIndicatorStrokeWidth = 75f,
                        hour = "01",
                        minitue = "00",
                        second = "36"
                    )
                    LargeButton(
                        text = "Start",
                        onClick = { },
                        containerColor = Green40,
                        textColor = Natural110
                    )
                }
            }
            storeList(list, null)

        }
    }
}

fun LazyListScope.storeList(list: List<Store>, navigator: Navigator?) {
    items(list) { item ->
        StoreCardItem(
            storeName = item.name,
            storeCode = item.code,
            taskCount = item.taskCount,
            passingTime = item.passedTime,
            completedTaskCount = item.completedTask,
            onClick = {
                navigator?.push(StoreDetailScreen(it))
            }
        )
    }
}