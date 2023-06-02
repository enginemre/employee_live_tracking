package com.hakmar.employeelivetracking.features.store_detail.domain.model

data class TaskModel(
    val name: String,
    var isCompleted: Boolean,
    val infoText: String,
    val imageUrl: String,
    val route: String,
)