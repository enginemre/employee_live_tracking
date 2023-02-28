package com.hakmar.employeelivetracking.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object BsStores : BottomBarScreen(
        route = "/stores",
        title = "BS",
        icon = Icons.Default.Home
    )

    object PmStores : BottomBarScreen(
        route = "/pm_stores",
        title = "Pm",
        icon = Icons.Default.Person
    )

    object Settings : BottomBarScreen(
        route = "/settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}