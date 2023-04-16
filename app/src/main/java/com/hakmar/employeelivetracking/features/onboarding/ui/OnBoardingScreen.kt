@file:OptIn(ExperimentalFoundationApi::class)

package com.hakmar.employeelivetracking.features.onboarding.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.features.auth.ui.LoginScreen
import com.hakmar.employeelivetracking.features.onboarding.domain.model.PermissionPagerModel
import com.hakmar.employeelivetracking.features.onboarding.ui.component.PageContent
import kotlinx.coroutines.launch

class OnBoardingScreen : Screen {

    companion object {
        val listOfPermission = mutableListOf(
            PermissionPagerModel(
                title = "Hoşgeldiniz",
                description = "Hemen giriş yapın ve mesainizi başlatın. Gününüzü değerlendirin.",
                rawRes = R.raw.work_team
            ),
            PermissionPagerModel(
                title = "Kamera Bilgisi",
                description = "Uygulama görevleri tamamlamanız için kameraya ihtiyac duymaktadır.",
                rawRes = R.raw.qr_code_scanner
            ),
            PermissionPagerModel(
                title = "Konum Bilgisi",
                description = "Uygulama çevrenizdeki en yakın mağazaların gösterecek bu yüzden konum bilgisine ihtiyaç duymaktadır ",
                rawRes = R.raw.map_pin_location
            )
        )
    }

    override val key: ScreenKey
        get() = Destination.OnBoarding.base

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val mainViewModel = getViewModel<MainViewModel>()
        val pageState = rememberPagerState(initialPage = 0)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S)
            listOfPermission.add(
                PermissionPagerModel(
                    title = "Bildirimler",
                    description = "Uygulama ihtiyacınız olduğunda ve sizi bildirmek istediğimizde size bildirim gönderecektir.",
                    rawRes = R.raw.notification
                )
            )
        var hasNotificationPermission by remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                )
            } else mutableStateOf(true)
        }
        var hasLocaitionPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        var hasCamPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        val launcherNotification = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
                navigator.replaceAll(LoginScreen())
                mainViewModel.setIsFirst()
            }
        )
        val launcherLocation = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasLocaitionPermission = isGranted
                if (listOfPermission.size == 3) {
                    navigator.replaceAll(LoginScreen())
                    mainViewModel.setIsFirst()
                } else {
                    coroutineScope.launch {
                        pageState.animateScrollToPage(listOfPermission.size)
                    }
                }

            }
        )
        val launcherCamera = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCamPermission = granted
                coroutineScope.launch {
                    pageState.animateScrollToPage(pageState.currentPage + 1)
                }
            }
        )
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pageState,
                pageCount = listOfPermission.size
            ) { page ->
                PageContent(
                    title = listOfPermission[page].title,
                    description = listOfPermission[page].description,
                    rawRes = listOfPermission[page].rawRes,
                    onClick = {
                        when (page) {
                            0 -> {
                                coroutineScope.launch {
                                    pageState.animateScrollToPage(1)
                                }
                            }

                            1 -> {
                                launcherCamera.launch(Manifest.permission.CAMERA)
                            }

                            2 -> {
                                launcherLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            }

                            else -> {
                                launcherNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }

                    }
                )
            }
        }
    }
}