package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

data class FabState(
    val onClick: (() -> Unit)? = null,
    val icon: ImageVector
)
