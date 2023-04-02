package com.hakmar.employeelivetracking.features.profile.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.DevicePreviews
import com.hakmar.employeelivetracking.common.presentation.ui.components.LoadingDialog
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.auth.ui.LoginScreen
import com.hakmar.employeelivetracking.features.notification.ui.NotificationScreen
import com.hakmar.employeelivetracking.features.profile.ui.component.ProfileHeader
import com.hakmar.employeelivetracking.features.profile.ui.component.ProfileItem
import com.hakmar.employeelivetracking.features.profile.ui.events.ProfileEvent
import com.hakmar.employeelivetracking.features.profile.ui.viewmodel.ProfileViewModel
import com.hakmar.employeelivetracking.util.UiEvent

class ProfileScreen : Screen {

    override val key: ScreenKey
        get() = HomeDestination.Profile.base

    @Composable
    override fun Content() {
        val mainViewModel = getViewModel<MainViewModel>()
        val viewModel = getViewModel<ProfileViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.currentOrThrow
        val title = stringResource(id = com.hakmar.employeelivetracking.R.string.profile)
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    isNavigationButton = false,
                    title = title
                )
            )
        }
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate<*> -> {
                        when (event.route) {
                            HomeDestination.Notification.base -> {
                                navigator.push(NotificationScreen())
                            }
                            ProfileDestination.EditProfile.base -> {
                                navigator.push(EditProfileScreen(state.value.email))
                            }
                            ProfileDestination.PrivatePolicy.base -> {
                                navigator.push(PrivatePolicyScreen())
                            }
                            ProfileDestination.AboutUs.base -> {
                                navigator.push(AboutScreen())
                            }
                            ProfileDestination.Logout.base -> {
                                mainViewModel.logOut()
                                navigator.parent?.parent?.replaceAll(LoginScreen())
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
        if (state.value.isLoading)
            LoadingDialog(stateLoading = state.value.isLoading)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp)
                )
                .padding(MaterialTheme.spacing.medium)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                    )
                    .fillMaxWidth()
                    .height(100.dp),
                painter = painterResource(id = com.hakmar.employeelivetracking.R.drawable.man),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            ProfileHeader(
                name = state.value.nameSurname
                    ?: stringResource(id = com.hakmar.employeelivetracking.R.string.default_name),
                mail = state.value.email
                    ?: stringResource(id = com.hakmar.employeelivetracking.R.string.default_email)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            state.value.menuList.forEach {
                ProfileItem(name = stringResource(id = it.name), icon = it.icon) {
                    viewModel.onEvent(ProfileEvent.OnProfileItemClick(it.destination))
                }
            }

        }
    }

}

@DevicePreviews
@Composable
fun ProfileScreenPrev() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    )
                    .fillMaxWidth()
                    .height(100.dp),
                painter = painterResource(id = com.hakmar.employeelivetracking.R.drawable.man),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            ProfileHeader(name = "Emre Muhammet Engin", mail = "emrengin@yaani.com")
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            LazyColumn {

            }

        }

    }

}