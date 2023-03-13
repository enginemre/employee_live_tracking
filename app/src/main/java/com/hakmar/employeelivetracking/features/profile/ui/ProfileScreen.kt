package com.hakmar.employeelivetracking.features.profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.features.profile.ui.component.ProfileItem
import com.hakmar.employeelivetracking.features.profile.ui.model.ProfileItemModel

@Composable
fun ProfileScreen(
    onEditProfile: () -> Unit
) {
    val list = listOf<ProfileItemModel>(
        ProfileItemModel(
            name = "About Us",
            icon = com.hakmar.employeelivetracking.R.drawable.info,
            onClick = onEditProfile
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
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BoxWithConstraints {
            val maxHeight = this.maxHeight
            if (maxHeight < 600.dp) {
                Column {
                    LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp)) {
                        items(list) {
                            ProfileItem(name = it.name, icon = it.icon) {
                                it.onClick()
                            }
                        }
                    }
                }
            } else {

            }

        }
    }
}