package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    actions: @Composable (RowScope.() -> Unit),
    isNavigationEnable: Boolean = false,
    navigationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500,
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colors.onSurface
        ),
        actions = actions,
        navigationIcon = {
            if (isNavigationEnable)
                IconButton(onClick = navigationClick) {
                    Image(
                        painter = painterResource(id = com.hakmar.employeelivetracking.R.drawable.back),
                        contentDescription = ""
                    )
                }

        }
    )
}