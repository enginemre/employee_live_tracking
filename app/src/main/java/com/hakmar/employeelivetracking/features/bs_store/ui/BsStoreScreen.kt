package com.hakmar.employeelivetracking.features.bs_store.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.nfc.NfcManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.domain.model.DistirctManager
import com.hakmar.employeelivetracking.common.domain.model.MarketingManager
import com.hakmar.employeelivetracking.common.domain.model.ProfileUser
import com.hakmar.employeelivetracking.common.domain.model.RegionalManager
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainEvent
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.*
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.bs_store.ui.component.CircleIndicator
import com.hakmar.employeelivetracking.features.bs_store.ui.component.StoreCardItem
import com.hakmar.employeelivetracking.features.nfc_reader.ui.NFCActivity
import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.features.qr_screen.ui.QrActivity
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailScreen
import com.hakmar.employeelivetracking.util.*

class BsStoreScreen : Screen {


    @SuppressLint("UnsafeOptInUsageError")
    @Composable
    override fun Content() {
        val mainViewModel = getViewModel<MainViewModel>()
        val viewModel = getViewModel<BsStoreViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        val launcherForNFC =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK)
                    mainViewModel.onEvent(
                        MainEvent.OnNfcRead(
                            it.data?.getStringExtra(
                                AppConstants.NFC_DATA
                            ), state.value.selectedStoreCode
                        )
                    )
                else
                    mainViewModel.onEvent(MainEvent.OnNfcDataNotRead)
            }
        val launcherForQr = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                if (it.resultCode == Activity.RESULT_OK)
                    mainViewModel.onEvent(
                        MainEvent.OnQRScan(
                            it.data?.getStringExtra(
                                AppConstants.QR_DATA
                            ), state.value.selectedStoreCode
                        )
                    )
                else
                    mainViewModel.onEvent(MainEvent.OnQrDataNotRead)
            })
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
                storeList(it, navigator = navigator) { storeCode ->
                    val store = it.find { store -> storeCode == store.code }
                    viewModel.onEvent(BsStoreEvent.OnStoreClick(store))
                    if (store != null && !store.isStoreShiftEnable) {
                        viewModel.onEvent(BsStoreEvent.ShowSnackBar)
                    } else {
                          if (!mainViewModel.isValidatedBefore(storeCode)) {
                              val adapter =
                                  (context.getSystemService(Context.NFC_SERVICE) as? NfcManager)?.defaultAdapter
                              if (adapter != null && adapter.isEnabled) {
                                  //Yes NFC available
                                  launcherForNFC.launch(Intent(context, NFCActivity::class.java))
                              } else if (adapter != null && !adapter.isEnabled) {
                                  //NFC is not enabled.Need to enable by the user.
                                  mainViewModel.onEvent(MainEvent.OnNfcNotOpened)
                              } else {
                                  //NFC is not supported
                                  launcherForQr.launch(Intent(context, QrActivity::class.java))
                              }

                          } else {
                              navigator.push(StoreDetailScreen(storeCode))
                          }
                    }

                }
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            areaCode = "001",
            regionalManager = RegionalManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            distirctManager = DistirctManager(
                "234",
                1,
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            marketingManager = MarketingManager(
                "001",
                1,
                ProfileUser(
                    "0034334",
                    "2342342",
                    "rsdfas",
                    userId = "2312",
                    user = User(nameSurname = "", "")
                )
            ),
            isStoreShiftEnable = false,
            address = ""
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
            storeList(list, null) {

            }

        }
    }
}

fun LazyListScope.storeList(list: List<Store>, navigator: Navigator?, onClick: (String) -> Unit) {

    items(list) { item ->
        StoreCardItem(
            storeName = item.name,
            storeCode = item.code,
            taskCount = item.taskCount,
            passingTime = item.passedTime,
            completedTaskCount = item.completedTask,
            onClick = {
                onClick(item.code)

                //navigator?.push(StoreDetailScreen(it))
            }
        )
    }
}