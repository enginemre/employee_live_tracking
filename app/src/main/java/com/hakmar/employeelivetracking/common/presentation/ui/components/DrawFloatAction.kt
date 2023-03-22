package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@Composable
fun DrawFloatAction(
    onFabClick: () -> Unit,
    icon: ImageVector
) {
    FloatingActionButton(
        onClick = { onFabClick() },
        shape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp,
            bottomEnd = 2.dp,
            bottomStart = 30.dp
        ),
        containerColor = MaterialTheme.colors.primary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 12.dp
        ),
        contentColor = Natural110
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}


@Preview()
@Composable
fun DrawFloatActionPrev() {
    EmployeeLiveTrackingTheme {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomEnd = 2.dp,
                bottomStart = 30.dp
            ),
            containerColor = MaterialTheme.colors.primary,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 12.dp
            ),
            contentColor = Natural110
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}