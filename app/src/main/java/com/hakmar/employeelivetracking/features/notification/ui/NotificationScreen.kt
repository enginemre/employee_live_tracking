package com.hakmar.employeelivetracking.features.notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.notification.domain.model.NotificationItem
import com.hakmar.employeelivetracking.features.notification.ui.component.NotificationItemCard
import com.hakmar.employeelivetracking.features.notification.ui.component.NotificationItemPrev

class NotificationScreen : Screen{

    override val key: ScreenKey
        get() = HomeDestination.Notification.base

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: NotificationViewModel = getViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val mainViewModel = getViewModel<MainViewModel>()
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    title = "Notification",
                    isNavigationButton = true,
                    navigationClick = { navigator.pop() },
                    actions = {
                        OverflowMenu {
                            DropdownMenuItem(onClick = { }, text = {
                                Text(
                                    text = "Tümünü Okundu İşaretle",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W400,
                                        lineHeight = 24.sp
                                    )
                                )
                            })
                        }
                    }
                )
            )
        }
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(NotificationEvent.OnRefreshNotification) }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = MaterialTheme.spacing.medium)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 55.dp, topStart = 55.dp)
                    ),
                contentPadding = PaddingValues(
                    vertical = MaterialTheme.spacing.medium,
                    horizontal = MaterialTheme.spacing.medium
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.notifications) {
                    NotificationItemCard(
                        it.title,
                        it.message,
                        it.dateText
                    )
                }
            }
        }


    }

}

@Preview(showSystemUi = true)
@Composable
fun NotificationScreenPrev() {
    EmployeeLiveTrackingTheme {
        val list = listOf<NotificationItem>(
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
            ),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MaterialTheme.spacing.medium)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topEnd = 55.dp, topStart = 55.dp)
                ),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list) {
                NotificationItemPrev()
            }
        }
    }
}

@Composable
fun OverflowMenu(content: @Composable () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = {
        showMenu = !showMenu
    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "Daha fazla",
        )
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        content()
    }
}
