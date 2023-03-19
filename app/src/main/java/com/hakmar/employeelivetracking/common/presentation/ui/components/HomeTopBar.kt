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
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
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
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = "Emre Engin", style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colors.onPrimary,
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