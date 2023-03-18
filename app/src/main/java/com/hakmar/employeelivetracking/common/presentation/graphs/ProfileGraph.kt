package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hakmar.employeelivetracking.common.presentation.base.MainViewModel
import com.hakmar.employeelivetracking.features.edit_profile.ui.EditProfile
import com.hakmar.employeelivetracking.features.profile.ui.ProfileScreen


@Composable
fun ProfileGraph(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel,
) {
    val routerProfile = RouterProfile(navController)
    NavHost(
        navController = navController,
        route = HomeDestination.Profile.base,
        startDestination = ProfileDestination.Home.base
    ) {
        composable(route = ProfileDestination.Home.base) {
            ProfileScreen(
                onEditProfile = { routerProfile.goEditProfile() },
                onAppBarConfig = {
                    mainViewModel.updateAppBar(it)
                }
            )
        }
        composable(route = ProfileDestination.EditProfile.base) {
            EditProfile(
                onBackPressed = {
                    navController.popBackStack()
                },
                onAppBarConfig = {
                    mainViewModel.updateAppBar(it)
                }
            )
        }
    }
}

private class RouterProfile(val navController: NavController) {
    fun goEditProfile() {
        navController.navigate(ProfileDestination.EditProfile.path)
    }
}

sealed interface ProfileDestination {
    val base: String
    val path: String

    object Home : ProfileDestination {
        override val base: String = "/"
        override val path: String = base
    }

    object EditProfile : ProfileDestination {
        override val base: String = "/edit_profile"
        override val path: String = base
    }
}