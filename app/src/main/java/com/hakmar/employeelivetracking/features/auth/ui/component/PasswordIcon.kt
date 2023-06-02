package com.hakmar.employeelivetracking.features.auth.ui.component


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@Composable
fun PasswordIcon(isActive: Boolean) {
    Icon(
        Icons.Default.Lock,
        contentDescription = "",
        tint = if (isActive) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
    )
}