package com.hakmar.employeelivetracking.features.tasks.domain

import androidx.compose.ui.graphics.Color

data class Task(
    val color: Color,
    val title: String,
    val description: String,
    val storeCode: String,
)
