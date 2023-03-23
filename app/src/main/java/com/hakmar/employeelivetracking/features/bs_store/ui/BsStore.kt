package com.hakmar.employeelivetracking.features.bs_store.ui

import android.content.Context
import android.nfc.NfcManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.LargeButton
import com.hakmar.employeelivetracking.common.presentation.ui.theme.*
import com.hakmar.employeelivetracking.common.service.GeneralShiftService
import com.hakmar.employeelivetracking.features.bs_store.ui.component.CircleIndicator
import com.hakmar.employeelivetracking.features.bs_store.ui.component.StoreCardItem
import com.hakmar.employeelivetracking.features.bs_store.ui.model.StoreCardModel
import com.hakmar.employeelivetracking.util.TimerState


@Composable
fun BsStoresScreen(
    viewModel: BsStoreViewModel = hiltViewModel(),
    generalShiftService: GeneralShiftService?,
    onStoreClick: (String) -> Unit,
) {
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
    LaunchedEffect(key1 = true) {
        generalShiftService?.dataUpdateListener =
            object : GeneralShiftService.GeneralShiftServiceListener {
                override fun onTick(h: String, m: String, s: String) {
                    viewModel.onTick(h, m, s)
                }
            }
    }
    val state = viewModel.state.collectAsState()

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
                            val adapter =
                                (context.getSystemService(Context.NFC_SERVICE) as NfcManager).defaultAdapter
                            if (adapter != null && adapter.isEnabled) {
                                //Yes NFC available
                            } else if (adapter != null && !adapter.isEnabled) {
                                //NFC is not enabled.Need to enable by the user.
                            } else {
                                //NFC is not supported
                            }
                            onStoreClick(it)
                        }
                    )
                }
            }

        }


    }
}

@Preview
@Composable
fun BsStoresScreenPreview() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface)
        ) {

        }
    }
}