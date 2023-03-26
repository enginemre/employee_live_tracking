package com.hakmar.employeelivetracking.features.bs_store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.ui.components.LargeButton
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.bs_store.ui.component.CircleIndicator
import com.hakmar.employeelivetracking.features.bs_store.ui.component.StoreCardItem
import com.hakmar.employeelivetracking.features.bs_store.ui.model.StoreCardModel
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailScreen
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.SystemReciver
import com.hakmar.employeelivetracking.util.TimerState

class BsStoreScreen : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val list = listOf(
            StoreCardModel(
                name = "Fatih Esenyalı",
                code = "5004",
                passedTime = "2 saat 12 dk",
                taskCount = 25,
                completedTask = 25,
            ),
            StoreCardModel(
                name = "Güzelyalı",
                code = "5024",
                passedTime = "1 saat 12 dk",
                taskCount = 25,
                completedTask = 12,
            ),
            StoreCardModel(
                name = "Gözdağı Pendik",
                code = "5054",
                passedTime = "12 dk",
                taskCount = 25,
                completedTask = 3,
            ),
            StoreCardModel(
                name = "Gülsuyu Maltepe",
                code = "5014",
                passedTime = "2 saat 12 dk",
                taskCount = 25,
                completedTask = 17,
            ),
            StoreCardModel(
                name = "Güzelyalı",
                code = "5024",
                passedTime = "1 saat 12 dk",
                taskCount = 25,
                completedTask = 12,
            ),
            StoreCardModel(
                name = "Gözdağı Pendik",
                code = "5054",
                passedTime = "12 dk",
                taskCount = 25,
                completedTask = 3,
            ),
            StoreCardModel(
                name = "Gülsuyu Maltepe",
                code = "5014",
                passedTime = "2 saat 12 dk",
                taskCount = 25,
                completedTask = 17,
            ),
        )
        val viewModel = getViewModel<BsStoreViewModel>()
        val state = viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        SystemReciver(action = AppConstants.ACTION_OBSERVE_GENERAL_SHIFT) {
            if (it?.action == AppConstants.ACTION_OBSERVE_GENERAL_SHIFT) {
                val string = it.getStringExtra(AppConstants.TIME_ELAPSED)
                val result = string!!.split(":")
                viewModel.onTick(result[0], result[1], result[2])
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircleIndicator(
                    indicatorValue = state.value.initialTime,
                    maxIndicatorValue = state.value.maxValueOfTime,
                    hour = state.value.hours,
                    minitue = state.value.minutes,
                    second = state.value.seconds
                )
                LargeButton(
                    text = if (state.value.isPlaying == TimerState.Started) "Durdur" else "Start",
                    onClick = viewModel::generalShiftButtonClick,
                    containerColor = if (state.value.isPlaying == TimerState.Started) Color.Red else Green40,
                    textColor = if (state.value.isPlaying == TimerState.Started) Color.White else Natural110
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 55.dp, topStart = 55.dp),
                    )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp),
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.small,
                        top = MaterialTheme.spacing.small
                    )
                ) {
                    items(list) { item ->
                        StoreCardItem(
                            storeName = item.name,
                            storeCode = item.code,
                            taskCount = item.taskCount,
                            passingTime = item.passedTime,
                            completedTaskCount = item.completedTask,
                            onClick = {

                                navigator.push(StoreDetailScreen())


                            }
                        )
                    }
                }

            }


        }
    }


}