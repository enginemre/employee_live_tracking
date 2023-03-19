package com.hakmar.employeelivetracking.common.presentation.graphs

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hakmar.employeelivetracking.common.Destination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.service.GeneralShiftService
import com.hakmar.employeelivetracking.features.bs_store.ui.BsStoresScreen
import com.hakmar.employeelivetracking.features.navigation.ui.NavigationScreen
import com.hakmar.employeelivetracking.features.notification.ui.NotificationScreen
import com.hakmar.employeelivetracking.features.pm_store.ui.PmStoreScreen
import com.hakmar.employeelivetracking.features.store_detail.ui.StoreDetailScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    generalShiftService: GeneralShiftService?
) {
    val routerHome = RouterHome(navController)
    LaunchedEffect(Unit) {
        val route = mainViewModel.getLastRoute()
        println(route)
        route?.let {
            navController.navigate(route)
        }
    }
    NavHost(
        navController = navController,
        route = Destination.Home.base,
        startDestination = HomeDestination.BsStores.base
    ) {
        composable(
            route = HomeDestination.BsStores.base,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRouter.baseUri + HomeDestination.BsStores.base
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = DeepLinkRouter.insideAppUri + HomeDestination.BsStores.base
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            BsStoresScreen(
                generalShiftService = generalShiftService,
                onStoreClick = {
                    routerHome.goBsStoreDetail(it)
                }
            )
        }
        composable(route = HomeDestination.PmStores.base) {
            PmStoreScreen()
        }
        composable(
            route = HomeDestination.Navigation.base,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRouter.insideAppUri + HomeDestination.Navigation.base
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = DeepLinkRouter.baseUri + HomeDestination.Navigation.base
                    action = Intent.ACTION_VIEW
                },
            )
        ) {
            NavigationScreen(
                mainViewModel = mainViewModel
            )
        }
        composable(route = HomeDestination.Profile.base) {
            ProfileGraph(
                mainViewModel = mainViewModel,
                onNotificationClick = {
                    routerHome.goNotification()
                }
            )
        }

        composable(
            route = HomeDestination.StoreDetail.path,
            arguments = listOf(navArgument("storeId") { type = NavType.StringType })
        ) {
            StoreDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onAppBarConfig = {
                    mainViewModel.updateAppBar(it)
                }
            )
        }
        composable(
            route = HomeDestination.Notification.base,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRouter.insideAppUri + HomeDestination.Notification.base
                    action = Intent.ACTION_VIEW
                },
                navDeepLink {
                    uriPattern = DeepLinkRouter.baseUri + HomeDestination.Notification.base
                    action = Intent.ACTION_VIEW
                },
            )
        ) {
            NotificationScreen(
                onAppBarConfig = { mainViewModel.updateAppBar(it) },
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}

private class RouterHome(val navController: NavController) {
    fun goBsStoreDetail(storeId: String) {
        navController.navigate("${HomeDestination.StoreDetail.base}/${storeId}")
    }

    fun goNotification() {
        navController.navigate(HomeDestination.Notification.base)
    }
}

val screens = listOf(
    HomeDestination.PmStores,
    HomeDestination.Tasks,
    HomeDestination.BsStores,
    HomeDestination.Navigation,
    HomeDestination.Profile,
)

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

    object Tasks : HomeDestination {
        override val base = "/tasks"
        override val path = "${base}/{taskId}"
        override val selectedIcon = Icons.Default.Task
        override val unSelectedIcon: ImageVector = Icons.Outlined.Task
    }

}