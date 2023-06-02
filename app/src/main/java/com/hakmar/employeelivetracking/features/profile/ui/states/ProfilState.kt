package com.hakmar.employeelivetracking.features.profile.ui.states

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.features.profile.ui.model.ProfileItemModel

data class ProfilState(
    var isLoading: Boolean = false,
    var nameSurname: String? = null,
    var email: String? = null,
    var menuList: List<ProfileItemModel> = listOf<ProfileItemModel>(
        ProfileItemModel(
            name = R.string.edit_profile,
            icon = R.drawable.profile_icon,
            destination = ProfileDestination.EditProfile.base
        ),
        ProfileItemModel(
            name = R.string.about_us,
            icon = R.drawable.info,
            destination = ProfileDestination.AboutUs.base
        ),
        ProfileItemModel(
            name = R.string.private_policy,
            icon = R.drawable.private_policy,
            destination = ProfileDestination.PrivatePolicy.base
        ),
        ProfileItemModel(
            name = R.string.notification,
            icon = R.drawable.notification,
            destination = HomeDestination.Notification.base
        ),
        ProfileItemModel(
            name = R.string.logout,
            icon = R.drawable.logout,
            destination = ProfileDestination.Logout.base
        )
    )
)
