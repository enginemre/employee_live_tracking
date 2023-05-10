package com.hakmar.employeelivetracking.features.auth.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@Composable
fun UserIcon(isActive: Boolean) {
    Icon(
        Icons.Default.Person,
        contentDescription = "",
        tint = if (isActive) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
    )
}