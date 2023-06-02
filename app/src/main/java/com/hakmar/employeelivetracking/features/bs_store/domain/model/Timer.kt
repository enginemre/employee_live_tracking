package com.hakmar.employeelivetracking.features.bs_store.domain.model

data class Timer(
    val hour : String,
    val minute : String,
    val second : String,
    var progress: Int
)
