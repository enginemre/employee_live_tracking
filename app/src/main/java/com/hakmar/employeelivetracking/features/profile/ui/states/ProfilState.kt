package com.hakmar.employeelivetracking.features.profile.ui.states

import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.features.profile.ui.model.ProfileItemModel

data class ProfilState(
    var isLoading: Boolean = false,
    var name: String? = null,
    var surname: String? = null,
    var email: String? = null,
    var menuList: List<ProfileItemModel> = listOf<ProfileItemModel>(
        ProfileItemModel(
            name = "Edit Profile",
            icon = com.hakmar.employeelivetracking.R.drawable.profile_icon,
            destination = ProfileDestination.EditProfile.base
        ),
        ProfileItemModel(
            name = "About Us",
            icon = com.hakmar.employeelivetracking.R.drawable.info,
            destination = ProfileDestination.AboutUs.base
        ),
        ProfileItemModel(
            name = "Private Policy",
            icon = com.hakmar.employeelivetracking.R.drawable.private_policy,
            destination = ProfileDestination.PrivatePolicy.base
        ),
        ProfileItemModel(
            name = "Notification",
            icon = com.hakmar.employeelivetracking.R.drawable.notification,
            destination = HomeDestination.Notification.base
        ),
        ProfileItemModel(
            name = "Logout",
            icon = com.hakmar.employeelivetracking.R.drawable.logout,
            destination = ProfileDestination.Logout.base
        )
    )
)
