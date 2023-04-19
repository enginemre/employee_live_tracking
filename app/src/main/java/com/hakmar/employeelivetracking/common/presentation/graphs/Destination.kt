package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material.icons.outlined.Task
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

    object Notification : HomeDestination {
        override val base = "/notification"
        override val path = base
    }

    object QRScreen : HomeDestination {
        override val base = "/qr_screen"
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

sealed interface TaskDestination {
    val base: String
    val path: String

    object TaskDetail : TaskDestination {
        override val base: String = "/task_detail"
        override val path: String = "$base/{taskId}"
    }
}

sealed interface ProfileDestination {
    val base: String
    val path: String

    object EditProfile : ProfileDestination {
        override val base = "/edit_profile"
        override val path = base
    }

    object AboutUs : ProfileDestination {
        override val base = "/about_us"
        override val path = base
    }

    object PrivatePolicy : ProfileDestination {
        override val base = "/private_policy"
        override val path = base
    }

    object Logout : ProfileDestination {
        override val base = "/log_out"
        override val path = base
    }
}

sealed interface StoreDetailDestination {
    val base: String
    val path: String

    object SteelCaseAmounts : StoreDetailDestination {
        override val base = HomeDestination.StoreDetail.base + "/steel_case_amaunt"
        override val path = base
    }

    object StoreOutside : StoreDetailDestination {
        override val base = HomeDestination.StoreDetail.base + "/store_out_control"
        override val path = base
    }

    object StoreInside : StoreDetailDestination {
        override val base = HomeDestination.StoreDetail.base + "/store_inside_control"
        override val path = base
    }

    object PosAmounts : StoreDetailDestination {
        override val base = HomeDestination.StoreDetail.base + "/store_z_report"
        override val path = base
    }
}
