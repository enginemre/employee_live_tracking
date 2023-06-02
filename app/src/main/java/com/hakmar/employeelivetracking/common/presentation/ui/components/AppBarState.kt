package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppBarState(
    val isNavigationButton: Boolean = false,
    val title: String = "",
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val navigationClick: (() -> Unit)? = null
)
