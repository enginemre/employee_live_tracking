package com.hakmar.employeelivetracking.features.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.profile.ui.component.ProfileHeader
import com.hakmar.employeelivetracking.features.profile.ui.component.ProfileItem
import com.hakmar.employeelivetracking.features.profile.ui.model.ProfileItemModel

@Composable
fun ProfileScreen(
    onEditProfile: () -> Unit,
    onNotification: () -> Unit,
    onAppBarConfig: (AppBarState) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        onAppBarConfig(
            AppBarState(
                isNavigationButton = false,
                title = "Profile"
            )
        )
    }
    val list = listOf<ProfileItemModel>(
        ProfileItemModel(
            name = "Edit Profile",
            icon = com.hakmar.employeelivetracking.R.drawable.profile_icon,
            onClick = onEditProfile
        ),
        ProfileItemModel(
            name = "About Us",
            icon = com.hakmar.employeelivetracking.R.drawable.info,
            onClick = {}
        ),
        ProfileItemModel(
            name = "Private Policy",
            icon = com.hakmar.employeelivetracking.R.drawable.private_policy,
            onClick = { }
        ),
        ProfileItemModel(
            name = "Notification",
            icon = com.hakmar.employeelivetracking.R.drawable.notification,
            onClick = onNotification
        ),
        ProfileItemModel(
            name = "Logout",
            icon = com.hakmar.employeelivetracking.R.drawable.logout,
            onClick = { }
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp))
            .padding(MaterialTheme.spacing.medium)
    ) {
        Image(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                )
                .fillMaxWidth()
                .height(100.dp),
            painter = painterResource(id = com.hakmar.employeelivetracking.R.drawable.man),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        ProfileHeader(name = "Emre Muhammet Engin", mail = "emrengin@yaani.com")
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        LazyColumn {
            items(list) {
                ProfileItem(name = it.name, icon = it.icon) {
                    it.onClick()
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPrev() {
    val list = listOf<ProfileItemModel>(
        ProfileItemModel(
            name = "Edit Profile",
            icon = com.hakmar.employeelivetracking.R.drawable.profile_icon,
            onClick = { }
        ),
        ProfileItemModel(
            name = "About Us",
            icon = com.hakmar.employeelivetracking.R.drawable.info,
            onClick = {}
        ),
        ProfileItemModel(
            name = "Private Policy",
            icon = com.hakmar.employeelivetracking.R.drawable.private_policy,
            onClick = { }
        ),
        ProfileItemModel(
            name = "Notification",
            icon = com.hakmar.employeelivetracking.R.drawable.notification,
            onClick = { }
        ),
        ProfileItemModel(
            name = "Logout",
            icon = com.hakmar.employeelivetracking.R.drawable.logout,
            onClick = { }
        )
    )
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    )
                    .fillMaxWidth()
                    .height(100.dp),
                painter = painterResource(id = com.hakmar.employeelivetracking.R.drawable.man),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            ProfileHeader(name = "Emre Muhammet Engin", mail = "emrengin@yaani.com")
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            LazyColumn {
                items(list) {
                    ProfileItem(name = it.name, icon = it.icon) {
                        it.onClick()
                    }
                }
            }

        }

    }

}