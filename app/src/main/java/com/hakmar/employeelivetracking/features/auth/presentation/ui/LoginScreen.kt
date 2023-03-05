@file:OptIn(ExperimentalComposeUiApi::class)

package com.hakmar.employeelivetracking.features.auth.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.components.*
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.auth.presentation.LoginEvent
import com.hakmar.employeelivetracking.features.auth.presentation.LoginFields
import com.hakmar.employeelivetracking.features.auth.presentation.component.LoginHeader
import com.hakmar.employeelivetracking.features.auth.presentation.component.LoginHeaderPreview
import com.hakmar.employeelivetracking.features.auth.presentation.component.PasswordIcon
import com.hakmar.employeelivetracking.features.auth.presentation.component.UserIcon
import com.hakmar.employeelivetracking.features.auth.presentation.viewmodel.LoginViewModel
import com.hakmar.employeelivetracking.util.UiEvent

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    windowSizeClass: WindowSizeClass,
    onLogin: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    onLogin()
                }
                else -> Unit
            }
        }
    }
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
                hint = "Kullanıcı adı",
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
                    viewModel.onEvent(LoginEvent.OnFocused(it, LoginFields.PasswordText))
                },
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnTextChange(it, LoginFields.PasswordText))
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
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        }


    }


}


@Preview("LoginScreen", showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    EmployeeLiveTrackingTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Giriş",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
            Surface(
                color = Color.White,
                modifier = Modifier
                    .padding(top = 75.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    LoginHeaderPreview()
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                    CustomTextFieldPrev()
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    CustomTextFieldPrev()
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    LargeButtonPrev()
                }

            }
        }

    }
}
