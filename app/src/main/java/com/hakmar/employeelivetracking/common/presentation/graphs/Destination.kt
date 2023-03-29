package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface HomeDestination {
    val base: String
    val path: String
    val selectedIcon: ImageVector?
        get() = null
    val unSelectedIcon: ImageVector?
        get() = null

    object BsStores : HomeDestination {
        override val base = "/stores"
        override val path = base
        override val selectedIcon = Icons.Filled.Home
        override val unSelectedIcon: ImageVector = Icons.Outlined.Home
    }

    object PmStores : HomeDestination {
        override val base = "/pm_stores"
        override val path = base
        override val selectedIcon = Icons.Filled.Store
        override val unSelectedIcon: ImageVector = Icons.Outlined.Store
    }

    object Navigation : HomeDestination {
        override val base = "/navigation"
        override val path = base
        override val selectedIcon = Icons.Filled.Navigation
        override val unSelectedIcon: ImageVector = Icons.Outlined.Navigation
    }

    object Tasks : HomeDestination {
        override val base = "/tasks"
        override val path = "${base}/{taskId}"
        override val selectedIcon = Icons.Default.Task
        override val unSelectedIcon: ImageVector = Icons.Outlined.Task
    }

    object Profile : HomeDestination {
        override val base = "/profile"
        override val path = base
        override val selectedIcon = Icons.Default.Person
        override val unSelectedIcon: ImageVector = Icons.Outlined.Person
    }

    object StoreDetail : HomeDestination {
        override val base = "/store"
        override val path = "$base/{storeId}"
    }

    object QRScreen : HomeDestination {
        override val base = "/qr_check"
        override val path = base
    }

    object Notification : HomeDestination {
        override val base = "/notification"
        override val path = base
    }
}

sealed interface Destination {
    val base: String
    val path: String

    object Auth : Destination {
        override val base: String = "/auth"
        override val path: String = base
    }

    object Home : Destination {
        override val base: String = "/home"
        override val path: String = base
    }

    object OnBoarding : Destination {
        override val base: String = "/onboarding"
        override val path: String = base
    }
}

