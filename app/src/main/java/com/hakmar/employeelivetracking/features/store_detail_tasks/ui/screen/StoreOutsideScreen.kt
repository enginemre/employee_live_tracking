package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.StoreDetailDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.DevicePreviews
import com.hakmar.employeelivetracking.common.presentation.ui.components.LargeButton
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.component.StoreCheckCard
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.StoreOutsideEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel.StoreOutsideViewModel


class StoreOutsideScreen : Screen {

    override val key: ScreenKey
        get() = StoreDetailDestination.StoreOutside.base

    @Composable
    override fun Content() {
        val mainViewModel: MainViewModel = getViewModel()
        val viewModel: StoreOutsideViewModel = getViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val checkList = viewModel.checkList
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
                        onChecked = { viewModel.onEvent(StoreOutsideEvent.OnChecked(checkItem)) }
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
                    onClick = { viewModel.onEvent(StoreOutsideEvent.CompleteCheck) })
            }
        }
    }
}

@DevicePreviews
@Composable
private fun StoreOutsideScreenPrev() {
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
                    count = 10
                ) { bodyIndex ->
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