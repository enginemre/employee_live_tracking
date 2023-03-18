package com.hakmar.employeelivetracking.features.edit_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState

@Composable
fun EditProfile(
    onAppBarConfig: (AppBarState) -> Unit,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        onAppBarConfig(
            AppBarState(
                title = "Profil Düzenle",
                isNavigationButton = true,
                navigationClick = onBackPressed
            )
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Profile")
    }
}