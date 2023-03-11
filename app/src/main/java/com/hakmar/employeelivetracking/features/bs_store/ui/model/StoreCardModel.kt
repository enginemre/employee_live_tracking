package com.hakmar.employeelivetracking.features.bs_store.ui.model

data class StoreCardModel(
    val name: String,
    val code: String,
    val passedTime: String,
    val taskCount: Int,
    val completedTask: Int
)
