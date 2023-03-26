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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.notification.domain.NotificationItem
import com.hakmar.employeelivetracking.features.notification.ui.component.NotificationItem
import com.hakmar.employeelivetracking.features.notification.ui.component.NotificationItemPrev

class NotificationScreen : Screen{

    override val key: ScreenKey
        get() = HomeDestination.Notification.base

    @Composable
    override fun Content() {
        val list = listOf<NotificationItem>(
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
            ),
        )
        val navigator = LocalNavigator.currentOrThrow
        val mainViewModel = getViewModel<MainViewModel>()
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    title = "Notification",
                    isNavigationButton = true,
                    navigationClick = {navigator.pop()},
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
            items(list) {
                NotificationItem()
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
                onClick = {}
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
            ),
            NotificationItem(
                title = "Bu bir başlık",
                message = "Kısa mesaj örneği",
                dateText = "15 dakika önce",
                onClick = {}
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
