package com.hakmar.employeelivetracking.features.tasks.domain.model

import androidx.compose.ui.graphics.Color

data class Task(
    val id: Int?,
    var color: Color?,
    var title: String,
    var description: String,
    var storeCode: String,
)
