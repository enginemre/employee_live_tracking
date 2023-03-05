package com.hakmar.employeelivetracking.features.auth.presentation.component


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun ShowPasswordIcon(passwordVisible: Boolean, onClick: () -> Unit) {
    val image = if (passwordVisible)
        Icons.Default.Close
    else Icons.Filled.Phone

    // Please provide localized description for accessibility services
    val description = if (passwordVisible) "Hide password" else "Show password"

    IconButton(onClick = { onClick() }) {
        Icon(imageVector = image, description)
    }
}