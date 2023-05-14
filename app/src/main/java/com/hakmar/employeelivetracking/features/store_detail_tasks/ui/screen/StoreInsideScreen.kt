package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
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
import com.hakmar.employeelivetracking.common.presentation.ui.components.LargeButton
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.component.StoreCheckCard
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.StoreInsideEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.TaskValidated
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel.StoreInsideScreenModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor

class StoreInsideScreen(val storeCode: String) : Screen {

    override val key: ScreenKey
        get() = StoreDetailDestination.StoreInside.base

    @Composable
    override fun Content() {
        val mainViewModel: MainViewModel = getViewModel()
        val viewModel =
            getScreenModel<StoreInsideScreenModel, StoreInsideScreenModel.Factory> { factory ->
                factory.create(storeCode)
            }
        val state by viewModel.state.collectAsStateWithLifecycle()
        val checkList = viewModel.checkList
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        val title = stringResource(id = R.string.store_inside_title)
        val navigator = LocalNavigator.currentOrThrow
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

                    is UiEvent.Navigate<*> -> {
                        when (event.route) {
                            HomeDestination.StoreDetail.base -> {
                                navigator.pop()
                                mainViewModel.postEvent(TaskValidated.StoreInsideValidated)
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
        LazyColumn {
            state.headers.forEach { header ->
                item(
                    contentType = "header"
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = MaterialTheme.spacing.small,
                                vertical = MaterialTheme.spacing.small
                            ),
                        text = header,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Natural110,
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                    )
                }
                items(checkList.filter { it.type == header }) { checkItem ->
                    StoreCheckCard(
                        modifier = Modifier.padding(
                            vertical = MaterialTheme.spacing.small,
                            horizontal = MaterialTheme.spacing.small
                        ),
                        checked = checkItem.completed,
                        description = checkItem.description,
                        onChecked = { viewModel.onEvent(StoreInsideEvent.OnChecked(checkItem)) }
                    )
                }
            }
            item {
                LargeButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.small,
                            vertical = MaterialTheme.spacing.medium
                        ),
                    text = stringResource(id = R.string.okey),
                    onClick = { viewModel.onEvent(StoreInsideEvent.CompleteCheck) })
            }
        }
    }


}

@DevicePreviews
@Composable
private fun StoreInsideScreenPrev() {
    EmployeeLiveTrackingTheme {
        LazyColumn {
            listOf(
                "Claim requested credentials",
                "Claim received credentials",
                "Pending Requests"
            ).forEach { header ->
                item(
                    contentType = "header"
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = MaterialTheme.spacing.small,
                                vertical = MaterialTheme.spacing.small
                            ),
                        text = header,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Natural110,
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                    )
                }
                items(
                    count = 2
                ) { _ ->
                    StoreCheckCard(
                        modifier = Modifier.padding(
                            vertical = MaterialTheme.spacing.small,
                            horizontal = MaterialTheme.spacing.small
                        ),
                        checked = true,
                        description = "sdsadkfdlsşkfldsaşakldsflşkdfsaşlkdfs",
                        onChecked = {}
                    )
                }
            }
            item {
                LargeButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.small,
                            vertical = MaterialTheme.spacing.medium
                        ),
                    text = "Onayla",
                    onClick = { })
            }
        }

    }
}