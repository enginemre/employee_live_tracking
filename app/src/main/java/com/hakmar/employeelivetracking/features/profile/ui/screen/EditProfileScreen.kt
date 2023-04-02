package com.hakmar.employeelivetracking.features.profile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.*
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.profile.ui.events.EditProfileEvent
import com.hakmar.employeelivetracking.features.profile.ui.events.EditProfileFields
import com.hakmar.employeelivetracking.features.profile.ui.viewmodel.EditProfileViewModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor
import kotlinx.coroutines.launch

class EditProfileScreen : Screen {

    override val key: ScreenKey
        get() = ProfileDestination.EditProfile.base

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val keyboardController = LocalSoftwareKeyboardController.current
        val mainViewModel = getViewModel<MainViewModel>()
        val viewModel = getViewModel<EditProfileViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val state = viewModel.state.collectAsStateWithLifecycle()
        val title = stringResource(id = com.hakmar.employeelivetracking.R.string.edit_profile)
        val snackScope = rememberCoroutineScope()
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    isNavigationButton = true,
                    title = title,
                    navigationClick = {
                        navigator.pop()
                    }
                )
            )
        }
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
                    else -> Unit
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    value = state.value.email,
                    label = stringResource(id = com.hakmar.employeelivetracking.R.string.email_label),
                    hint = "exapmle@gmail.com",
                    keyboardType = KeyboardType.Email,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.OnTextChange(
                                it,
                                EditProfileFields.Email
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    value = state.value.oldPassword,
                    label = stringResource(id = com.hakmar.employeelivetracking.R.string.old_password_label),
                    hint = "******",
                    keyboardType = KeyboardType.Password,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.OnTextChange(
                                it,
                                EditProfileFields.OldPasswod
                            )
                        )
                    },
                    visualTransformation = if (state.value.isVisibleOldPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(EditProfileEvent.ChangeVisibilty(EditProfileFields.OldPasswod))
                        }) {
                            Icon(
                                imageVector = if (state.value.isVisibleOldPassword)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff, null
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    value = state.value.newPassword,
                    label = stringResource(id = com.hakmar.employeelivetracking.R.string.new_password_label),
                    hint = "******",
                    keyboardType = KeyboardType.Password,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.OnTextChange(
                                it,
                                EditProfileFields.NewPassword
                            )
                        )
                    },
                    visualTransformation = if (state.value.isVisibleNewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(EditProfileEvent.ChangeVisibilty(EditProfileFields.NewPassword))
                        }) {
                            Icon(
                                imageVector = if (state.value.isVisibleNewPassword)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff, null
                            )
                        }
                    },
                    imeAction = ImeAction.Done,
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            }
            LargeButton(modifier = Modifier
                .weight(1f, false)
                .padding(MaterialTheme.spacing.large)
                .fillMaxWidth(),
                text = stringResource(id = com.hakmar.employeelivetracking.R.string.save),
                onClick = {
                    viewModel.onEvent(EditProfileEvent.OnSaveClick)
                })
        }
    }
}


@DevicePreviews
@Composable
fun EditProfilePrev() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    value = "",
                    label = "Email",
                    hint = "emre@engin",
                    keyboardType = KeyboardType.Email,
                    onValueChange = {}
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    value = "",
                    label = "Yeni Şifre",
                    hint = "******",
                    keyboardType = KeyboardType.Password,
                    onValueChange = {}
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    value = "",
                    label = "Yeni Şifre",
                    hint = "******",
                    keyboardType = KeyboardType.Password,
                    onValueChange = {}
                )
            }
            LargeButton(modifier = Modifier
                .weight(1f, false)
                .padding(MaterialTheme.spacing.large)
                .fillMaxWidth(), text = "Onayla", onClick = { })
        }
    }
}