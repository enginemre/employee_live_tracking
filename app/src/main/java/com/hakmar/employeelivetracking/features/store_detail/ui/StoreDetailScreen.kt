package com.hakmar.employeelivetracking.features.store_detail.ui

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.StoreDetailDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackbarVisuals
import com.hakmar.employeelivetracking.common.presentation.ui.components.DevicePreviews
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel
import com.hakmar.employeelivetracking.features.store_detail.ui.component.*
import com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel.StoreDetailScreenModel
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen.PosScreen
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen.SteelCaseAmountScreen
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen.StoreInsideScreen
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen.StoreOutsideScreen
import com.hakmar.employeelivetracking.util.*

@ExperimentalGetImage
class StoreDetailScreen(
    private val storeCode: String
) : AndroidScreen() {

    override val key: ScreenKey
        get() = HomeDestination.StoreDetail.base

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val mainViewModel = getViewModel<MainViewModel>()
        val screenModel =
            getScreenModel<StoreDetailScreenModel, StoreDetailScreenModel.Factory> { factory ->
                factory.create(storeCode)
            }
        val context = LocalContext.current
        val snackbarHostState = LocalSnackbarHostState.current
        val pageState = rememberPagerState()
        val navigator = LocalNavigator.currentOrThrow
        val title = stringResource(id = R.string.store_detail)
        val state by screenModel.state.collectAsStateWithLifecycle()
        SystemReciver(action = AppConstants.ACTION_OBSERVE_STORE_SHIFT) {
            if (it?.action == AppConstants.ACTION_OBSERVE_STORE_SHIFT) {
                val string = it.getStringExtra(AppConstants.TIME_ELAPSED)
                val result = string!!.split(":")
                screenModel.onEvent(StoreDetailEvent.OnTick(result[0], result[1], result[2]))
            }
        }
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    title = title,
                    isNavigationButton = true,
                    navigationClick = { navigator.pop() }
                )
            )
        }
        LaunchedEffect(key1 = Unit) {
            screenModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.message.asString(context),
                                containerColor = getContainerColor(event.type),
                                contentColor = getContentColor(event.type)
                            )
                        )
                    }

                    is UiEvent.Navigate<*> -> {
                        when (event.route) {
                            StoreDetailDestination.StoreInside.base -> {
                                navigator.push(StoreInsideScreen(storeCode))
                            }

                            StoreDetailDestination.StoreOutside.base -> {
                                navigator.push(StoreOutsideScreen(storeCode))
                            }

                            StoreDetailDestination.SteelCaseAmounts.base -> {
                                navigator.push(
                                    SteelCaseAmountScreen(
                                        storeCode,
                                        state.store?.name ?: ""
                                    )
                                )
                            }

                            StoreDetailDestination.PosAmounts.base -> {
                                navigator.push(PosScreen(storeCode, state.store?.name ?: ""))
                            }

                            else -> {
                                navigator.pop()
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StoreShiftCard(
                hour = state.hours,
                minutue = state.minutes,
                second = state.seconds,
                indicatorValue = state.initialTime,
                maxIndicValue = state.maxValueOfTime,
                positiveIcon = if (state.isPlaying == TimerState.Started) Icons.Default.Pause else Icons.Default.PlayArrow,
                isStopIconVisible = state.isPlaying != TimerState.Idle,
                onStopClick = { screenModel.onEvent(StoreDetailEvent.OnStopButtonClick) },
                onPositiveClick = {
                    screenModel.onEvent(
                        StoreDetailEvent.OnActionButtonClick(
                            storeCode
                        )
                    )
                }
            )
            HorizontalPager(
                pageCount = 2,
                state = pageState
            ) { page ->
                if (page == 0) {
                    StoreInfoCard(
                        storeName = state.store?.name ?: "Store Name",
                        storeCode = state.store?.code ?: "****",
                        bsName = state.store?.distirctManager?.profile?.user?.nameSurname ?: "",
                        pmName = state.store?.marketingManager?.profile?.user?.nameSurname ?: "",
                        address = "Çınardere mah gençlik cad no 16 daire 15 pendik İsitanbul"
                    )
                } else {
                    TaskProgressCard(
                        modifier = Modifier,
                        taskCount = state.store?.taskCount ?: 1,
                        completedTask = state.store?.completedTask ?: 0
                    )
                }
            }
            LayoutTitle(
                modifier = Modifier.widthIn(min = 400.dp),
                title = stringResource(id = R.string.task_section_title),
                link = stringResource(id = R.string.see_all)
            )
            LazyRow(
                contentPadding = PaddingValues(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.large,
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.taskList) {
                    TaskCard(name = it.name, imageUrl = it.imageUrl, it.isCompleted) {
                        screenModel.onEvent(StoreDetailEvent.OnTaskClick(it))
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@DevicePreviews
@Composable
private fun StoreDetailScreenPrev(
) {
    val list = listOf<TaskModel>(
        TaskModel(
            name = "Çelik Kasa Sayım",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/2676/2676632.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle kasanın yanına git",
            route = StoreDetailDestination.SteelCaseAmounts.base
        ),
        TaskModel(
            name = "Mağaza Önü Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/609/609752.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git",
            route = StoreDetailDestination.StoreOutside.base
        ),
        TaskModel(
            name = "Mağaza İçi Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/3306/3306049.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git",
            route = StoreDetailDestination.StoreInside.base

        ),
        TaskModel(
            name = "Z Raporları Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/9342/9342023.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git",
            route = StoreDetailDestination.PosAmounts.base
        ),
    )
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StoreShiftCard(
                hour = "00",
                minutue = "00",
                second = "00",
                indicatorValue = 50,
                maxIndicValue = 100,
                positiveIcon = Icons.Default.PlayArrow,
                isStopIconVisible = false,
                onStopClick = { },
                onPositiveClick = { }
            )
            HorizontalPager(
                pageCount = 2,
                state = rememberPagerState()
            ) { page ->
                if (page == 0) {
                    StoreInfoCard(
                        storeName = "Pendik Fatih Esenyalı",
                        storeCode = "5004",
                        bsName = "Ahmet Kaya",
                        pmName = "İbrahim Şakar",
                        address = "Çınardere mah gençlik cad no 16 daire 15 pendik İsitanbul"
                    )
                } else {
                    TaskProgressCard(
                        modifier = Modifier,
                        taskCount = 12,
                        completedTask = 7
                    )
                }
            }
            LayoutTitle(
                modifier = Modifier.widthIn(min = 400.dp),
                title = "Görevler",
                link = "Tümünü Gör"
            )
            LazyRow(
                contentPadding = PaddingValues(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.large,
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(list) {
                    TaskCard(name = it.name, imageUrl = it.imageUrl, it.isCompleted) {

                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        }
    }

}
