package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.StoreDetailDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackbarVisuals
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomTextField
import com.hakmar.employeelivetracking.common.presentation.ui.components.DevicePreviews
import com.hakmar.employeelivetracking.common.presentation.ui.components.LargeButton
import com.hakmar.employeelivetracking.common.presentation.ui.components.LoadingDialog
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.SteelCaseAmountField
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel.SteelCaseAmountViewModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor

class SteelCaseAmountScreen(
    val storeCode: String,
    val storeName: String,
) : Screen {

    override val key: ScreenKey
        get() = StoreDetailDestination.SteelCaseAmounts.base

    @Composable
    override fun Content() {
        val viewModel: SteelCaseAmountViewModel = getViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val mainViewModel: MainViewModel = getViewModel()
        val title = stringResource(id = R.string.steel_case_amoun_label)
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val snackbarHostState = LocalSnackbarHostState.current
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
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.message.asString(context),
                                contentColor = getContentColor(event.type),
                                containerColor = getContainerColor(event.type)
                            )
                        )
                    }

                    else -> Unit
                }
            }
        }
        if (state.isLoading) {
            LoadingDialog(stateLoading = state.isLoading)
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    text = "$storeCode $storeName",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        color = Natural110
                    )
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = state.banknotes,
                    label = stringResource(id = R.string.banknotes),
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {
                        viewModel.onEvent(
                            SteelCaseAmountEvent.OnTextChange(
                                it,
                                type = SteelCaseAmountField.Banknotes
                            )
                        )
                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = state.coins,
                    label = stringResource(id = R.string.coins),
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {
                        viewModel.onEvent(
                            SteelCaseAmountEvent.OnTextChange(
                                it,
                                type = SteelCaseAmountField.Coins
                            )
                        )
                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = state.totalPosAmount,
                    label = stringResource(id = R.string.total_pos_amount_label),
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {
                        viewModel.onEvent(
                            SteelCaseAmountEvent.OnTextChange(
                                it,
                                type = SteelCaseAmountField.TotalPos
                            )
                        )
                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = state.totalPayment,
                    label = stringResource(id = R.string.total_payment),
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {
                        viewModel.onEvent(
                            SteelCaseAmountEvent.OnTextChange(
                                it,
                                type = SteelCaseAmountField.TotalPayments
                            )
                        )
                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = state.steelCaseAmount,
                    label = stringResource(id = R.string.steel_case_amoun_label),
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {
                        viewModel.onEvent(
                            SteelCaseAmountEvent.OnTextChange(
                                it,
                                type = SteelCaseAmountField.SteelCaseAmount
                            )
                        )
                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = state.difference,
                    label = stringResource(id = R.string.difference_amount_label),
                    hint = "1000",
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {
                        viewModel.onEvent(
                            SteelCaseAmountEvent.OnTextChange(
                                it,
                                type = SteelCaseAmountField.Difference
                            )
                        )
                    })
            }
            LargeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.large
                    ),
                text = stringResource(id = R.string.okey),
                onClick = {
                    viewModel.onEvent(
                        SteelCaseAmountEvent.OnCompleteSteelCaseAmount(
                            storeCode
                        )
                    )
                })


        }
    }
}


@DevicePreviews
@Composable
private fun SteelCaseAmountScreenPrev() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    text = "5004 Tabakhane / Serdivan",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        color = Natural110
                    )
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = "",
                    label = "Banknotes",
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {})
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = "",
                    label = "Coins",
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {})
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = "",
                    label = "Total Pos Amount",
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {})
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = "",
                    label = "Total Expenses and Payments",
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {})
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = "",
                    label = "Steel Case Amount",
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {})
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                CustomTextField(
                    value = "",
                    label = "Difference",
                    hint = "1000",
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal,
                    onValueChange = {})
            }
            LargeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.large
                    ),
                text = "Onayla",
                onClick = { })

        }
    }
}