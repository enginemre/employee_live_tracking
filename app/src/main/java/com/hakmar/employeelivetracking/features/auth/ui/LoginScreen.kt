package com.hakmar.employeelivetracking.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.HomeScreen
import com.hakmar.employeelivetracking.common.presentation.graphs.Destination
import com.hakmar.employeelivetracking.common.presentation.ui.components.*
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.auth.presentation.LoginEvent
import com.hakmar.employeelivetracking.features.auth.presentation.LoginFields
import com.hakmar.employeelivetracking.features.auth.presentation.ui.component.LoginHeader
import com.hakmar.employeelivetracking.features.auth.presentation.ui.component.PasswordIcon
import com.hakmar.employeelivetracking.features.auth.presentation.ui.component.UserIcon
import com.hakmar.employeelivetracking.features.auth.ui.viewmodel.LoginViewModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor
import kotlinx.coroutines.launch

class LoginScreen : Screen {

    override val key: ScreenKey
        get() = Destination.Auth.base

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<LoginViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val keyboardController = LocalSoftwareKeyboardController.current
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }
        val snackScope = rememberCoroutineScope()
        val state = viewModel.state.collectAsState()
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        snackScope.launch {
                            snackbarHostState.showSnackbar(
                                CustomSnackbarVisuals(
                                    message = event.message.asString(context),
                                    contentColor = getContentColor(event.type),
                                    containerColor = getContainerColor(event.type)
                                )
                            )
                        }

                    }
                    is UiEvent.Navigate<*> -> {
                        navigator.replaceAll(HomeScreen())
                    }
                    else -> Unit
                }
            }
        }
        Scaffold(
            snackbarHost = { CustomSnackBar(snackbarHostState) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 64.dp)
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(topEnd = 55.dp, topStart = 55.dp),
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(35.dp)
                ) {
                    if (state.value.isLoading) {
                        LoadingDialog(stateLoading = state.value.isLoading)
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    LoginHeader()
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CustomTextField(
                            Modifier.widthIn(max = 380.dp),
                            value = state.value.usercode,
                            leadingIcon = { UserIcon(isActive = state.value.isActiveUser) },
                            label = stringResource(id = R.string.user_name),
                            hint = stringResource(id = R.string.user_name),
                            keyboardType = KeyboardType.Text,
                            onFocused = {
                                viewModel.onEvent(LoginEvent.OnFocused(it, LoginFields.UserText))
                            },
                            onValueChange = {
                                viewModel.onEvent(LoginEvent.OnTextChange(it, LoginFields.UserText))
                            },
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                        CustomTextField(
                            modifier = Modifier.widthIn(max = 380.dp),
                            value = state.value.password,
                            label = stringResource(id = R.string.password),
                            hint = "*****",
                            leadingIcon = { PasswordIcon(isActive = state.value.isActivePassword) },
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                            visualTransformation = PasswordVisualTransformation(),
                            onFocused = {
                                viewModel.onEvent(
                                    LoginEvent.OnFocused(
                                        it,
                                        LoginFields.PasswordText
                                    )
                                )
                            },
                            onValueChange = {
                                viewModel.onEvent(
                                    LoginEvent.OnTextChange(
                                        it,
                                        LoginFields.PasswordText
                                    )
                                )
                            },
                            onDone = {
                                keyboardController?.hide()
                            }
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                        LargeButton(
                            modifier = Modifier.widthIn(min = 350.dp, max = 400.dp),
                            text = stringResource(id = R.string.login),
                            onClick = {
                                viewModel.onEvent(LoginEvent.OnLoginClick)
                            })
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    }


                }
            }
        }

    }

}

@DevicePreviews
@Composable
fun LoginScreenPrev() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 64.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(topEnd = 55.dp, topStart = 55.dp),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            /*if (state.value.isLoading) {
                LoadingDialog(stateLoading = state.value.isLoading)
            }*/
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            LoginHeader()
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomTextField(
                    Modifier.widthIn(max = 380.dp),
                    value = "",
                    leadingIcon = { UserIcon(isActive = true) },
                    label = stringResource(id = R.string.user_name),
                    hint = "Kullanıcı adı",
                    keyboardType = KeyboardType.Text,
                    onFocused = {

                    },
                    onValueChange = {

                    },
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    modifier = Modifier.widthIn(max = 380.dp),
                    value = "",
                    label = stringResource(id = R.string.password),
                    hint = "*****",
                    leadingIcon = { PasswordIcon(isActive = true) },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    visualTransformation = PasswordVisualTransformation(),
                    onFocused = {

                    },
                    onValueChange = {

                    },
                    onDone = {

                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                LargeButton(
                    modifier = Modifier.widthIn(min = 350.dp, max = 400.dp),
                    text = stringResource(id = R.string.login),
                    onClick = {

                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            }


        }
    }
}