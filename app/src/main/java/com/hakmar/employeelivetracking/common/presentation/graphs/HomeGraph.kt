package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Store
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hakmar.employeelivetracking.common.Destination
import com.hakmar.employeelivetracking.common.presentation.base.MainViewModel
import com.hakmar.employeelivetracking.common.service.GeneralShiftService
import com.hakmar.employeelivetracking.features.bs_store.ui.BsStoresScreen
import com.hakmar.employeelivetracking.features.pm_store.ui.PmStoreScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    generalShiftService: GeneralShiftService?
) {
    val routerHome = RouterHome(navController)
    NavHost(
        navController = navController,
        route = Destination.Home.base,
        startDestination = HomeDestination.BsStores.base
    ) {
        composable(route = HomeDestination.BsStores.base) {
            BsStoresScreen(
                generalShiftService = generalShiftService
            )
        }
        composable(route = HomeDestination.PmStores.base) {
            PmStoreScreen(

                /*onBackPressed = {
                    navController.popBackStack()
                },
                onAppBarConfig = {
                    mainViewModel.updateAppBar(it)
                }*/
            )
        }
        composable(route = HomeDestination.Navigation.base) {

        }
        composable(route = HomeDestination.Profile.base) {

        }
    }
}

private class RouterHome(val navController: NavController) {
    fun goPMStoreScreen() {
        navController.navigate(HomeDestination.PmStores.base)
    }
}

val screens = listOf(
    HomeDestination.PmStores,
    HomeDestination.BsStores,
    HomeDestination.Navigation,
    HomeDestination.Profile,
)

sealed interface HomeDestination {
    val base: String
    val path: String
    val selectedIcon: ImageVector
    val unSelectedIcon: ImageVector

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


}