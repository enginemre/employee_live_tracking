package com.hakmar.employeelivetracking.features.store_detail.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.nfc.NfcManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.nfc_reader.ui.NFCActivity
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel
import com.hakmar.employeelivetracking.features.store_detail.ui.component.*
import com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel.StoreDetailViewModel
import com.hakmar.employeelivetracking.util.*
import kotlin.math.absoluteValue

class StoreDetailScreen(
    private val storeCode : String
) : Screen {

    override val key: ScreenKey
        get() = HomeDestination.StoreDetail.base



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val list = listOf<TaskModel>(
            TaskModel(
                name = "Çelik Kasa Sayım",
                isCompleted = true,
                imageUrl = "https://cdn-icons-png.flaticon.com/512/2676/2676632.png",
                infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle kasanın yanına git"
            ),
            TaskModel(
                name = "Mağaza Önü Kontrol",
                isCompleted = false,
                imageUrl = "https://cdn-icons-png.flaticon.com/512/609/609752.png",
                infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
            ),
            TaskModel(
                name = "Mağaza İçi Kontrol",
                isCompleted = false,
                imageUrl = "https://cdn-icons-png.flaticon.com/512/3306/3306049.png",
                infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
            ),
            TaskModel(
                name = "Z Raporları Kontrol",
                isCompleted = false,
                imageUrl = "https://cdn-icons-png.flaticon.com/512/9342/9342023.png",
                infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
            ),
        )
        val pageState = rememberPagerState()
        val mainViewModel = getViewModel<MainViewModel>()
        val viewModel = getViewModel<StoreDetailViewModel>()
        val context = LocalContext.current
        val snackbarHostState = LocalSnackbarHostState.current
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK)
                    viewModel.getNFCData(it.data?.getStringExtra(AppConstants.NFC_DATA))
            }
        SystemReciver(action = AppConstants.ACTION_OBSERVE_STORE_SHIFT) {
            if (it?.action == AppConstants.ACTION_OBSERVE_STORE_SHIFT) {
                val string = it.getStringExtra(AppConstants.TIME_ELAPSED)
                val result = string!!.split(":")
                viewModel.onTick(result[0], result[1], result[2])
            }
        }
        val navigator = LocalNavigator.currentOrThrow
        val state = viewModel.state.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = true) {
            mainViewModel.updateAppBar(
                AppBarState(
                    title = "Mağaza Detay",
                    isNavigationButton = true,
                    navigationClick = { navigator.pop() }
                )
            )
        }
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.QrScnaned -> {
                        val result =  decodeJwt(event.data)
                          snackbarHostState.showSnackbar(
                            message = UiText.DynamicString("QR Okundu : $result ")
                                .asString(context)
                        )
                        Log.e("QR Process",result.toString())
                    }
                    is UiEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message.asString(context)
                        )
                    }
                    else -> {}
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            val adapter =
                (context.getSystemService(Context.NFC_SERVICE) as? NfcManager)?.defaultAdapter
            if (adapter != null && adapter.isEnabled) {
                //Yes NFC available
                launcher.launch(Intent(context, NFCActivity::class.java))
            } else if (adapter != null && !adapter.isEnabled) {
                //NFC is not enabled.Need to enable by the user.
                viewModel.nfcShouldBeOpen()
            } else {
                //NFC is not supported
                viewModel.startScanning()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StoreShiftCard(
                hour = state.value.hours,
                minutue = state.value.minutes,
                second = state.value.seconds,
                indicatorValue = state.value.initialTime,
                maxIndicValue = state.value.maxValueOfTime,
                positiveIcon = if (state.value.isPlaying == TimerState.Started) Icons.Default.Pause else Icons.Default.PlayArrow,
                isStopIconVisible = state.value.isPlaying != TimerState.Idle,
                onStopClick = { viewModel.stopButtonClick() },
                onPositiveClick = { viewModel.actionButtonClick() }
            )
            HorizontalPager(
                pageCount = 2,
                state = pageState
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
                    Card(
                        Modifier
                            .widthIn(max = 400.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                top = MaterialTheme.spacing.large,
                                start = MaterialTheme.spacing.large,
                                end = MaterialTheme.spacing.large
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = MaterialTheme.spacing.medium),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CompletedStatusProgressBar(
                                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                                percentage = 12 / 25f,
                                taskCount = 25,
                                size = 100.dp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(12.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Bügünkü görevlerinizi tamamlamaya erkenden başlayın ve hedeflerinize ulaşın",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Natural80,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.W500
                                )
                            )
                        }
                    }
                }
            }
            LayoutTitle(title = "Görevler", link = "Tümünü Gör")
            TaskList(list)

        }
    }

}

@Composable
fun TaskList(
    list: List<TaskModel>
) {
    LazyRow(
        contentPadding = PaddingValues(
            top = MaterialTheme.spacing.medium,
            start = MaterialTheme.spacing.large,
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(list) {
            TaskCard(name = it.name, imageUrl = it.imageUrl, it.isCompleted)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showSystemUi = true)
@Composable
fun StoreDetailScreenPrev(
) {
    val list = listOf<TaskModel>(
        TaskModel(
            name = "Çelik Kasa Sayım",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/2676/2676632.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle kasanın yanına git"
        ),
        TaskModel(
            name = "Mağaza Önü Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/609/609752.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
        TaskModel(
            name = "Mağaza İçi Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/3306/3306049.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
        TaskModel(
            name = "Z Raporları Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/9342/9342023.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
    )
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pageState = rememberPagerState(
                initialPageOffsetFraction = 0.1f
            )
            HorizontalPager(
                pageCount = 2,
                state = pageState,
            ) { page ->
                if (page == 0) {
                    Card(
                        Modifier
                            .widthIn(max = 400.dp)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pageState.currentPage - page) + pageState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                            .height(225.dp)
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                        }


                    }
                } else {

                    Card(
                        Modifier
                            .widthIn(max = 400.dp)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pageState.currentPage - page) + pageState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                            .height(225.dp)
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Pendik Fatih Esenyalı",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Kod : 5004",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 14.sp,
                                    color = Natural80,
                                    fontWeight = FontWeight.W500,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Bölge Sorumlusu : Ahmet Kaya",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 14.sp,
                                    color = Natural80,
                                    fontWeight = FontWeight.W500,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Pazarlama Müdürü : İbrahim Şakar",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 14.sp,
                                    color = Natural80,
                                    fontWeight = FontWeight.W500,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Adres : Çınardere Mah gençlik cad no 134 daire 16 kat 5 mer pendik istanbul ",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 14.sp,
                                    color = Natural80,
                                    fontWeight = FontWeight.W500,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                        }


                    }
                }
            }
            Card(
                Modifier
                    .widthIn(max = 400.dp)
                    .height(225.dp)
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Pendik Fatih Esenyalı",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kod : 5004",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Natural80,
                            fontWeight = FontWeight.W500,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Bölge Sorumlusu : Ahmet Kaya",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Natural80,
                            fontWeight = FontWeight.W500,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Pazarlama Müdürü : İbrahim Şakar",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Natural80,
                            fontWeight = FontWeight.W500,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Adres : Çınardere Mah gençlik cad no 134 daire 16 kat 5 mer pendik istanbul ",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Natural80,
                            fontWeight = FontWeight.W500,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }


            }
            Card(
                Modifier
                    .widthIn(max = 400.dp)
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        top = MaterialTheme.spacing.large,
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CompletedStatusProgressBar(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                        percentage = 12 / 25f,
                        taskCount = 25,
                        size = 100.dp
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = "Bügünkü görevlerinizi tamamlamaya erkenden başlayın ve hedeflerinize ulaşın",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Natural80,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .padding(
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.large
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Görevler", style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp
                    )
                )
                Text(
                    text = "Tümünü Gör", style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Natural80,
                        fontWeight = FontWeight.W400
                    )
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .widthIn(max = 500.dp),
                contentPadding = PaddingValues(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.large,
                    end = MaterialTheme.spacing.large
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(list) {
                    TaskCard(name = it.name, imageUrl = it.imageUrl, isCompleted = false)
                }
            }
        }
    }

}
