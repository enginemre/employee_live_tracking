package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    name: String,
    onNotificonClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.man),
                contentDescription = null,
                modifier = Modifier
                    .padding(12.dp)
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        },
        title = {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Merhaba 👋", style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        color = Natural110
                    )
                )
                Text(
                    text = name, style = MaterialTheme.typography.titleMedium.copy(
                        color = Natural110,
                    )
                )
            }
        },
        actions = {
            TopAppBarActionButton(
                imageVector = Icons.Outlined.Notifications,
                description = "Bildirimler"
            ) {
                onNotificonClick()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}