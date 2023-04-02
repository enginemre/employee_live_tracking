package com.hakmar.employeelivetracking.common.domain.model

data class Store(
    val id: String,
    val code: String,
    val name: String,
    var completedTask: Int,
    val taskCount: Int,
    val passedTime: String,
    val longtitude: Double,
    val lattitude: Double,
    val areaCode: String,
)
