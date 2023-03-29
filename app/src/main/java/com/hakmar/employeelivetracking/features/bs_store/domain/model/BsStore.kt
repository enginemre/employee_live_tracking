package com.hakmar.employeelivetracking.features.bs_store.domain.model

data class BsStore(
    val id : String,
    val code : String,
    val name : String,
    var completedTask : Int,
    val taskCount : Int,
    val passedTime : String,
    val longtitude : Double,
    val lattitude :Double,
    val areaCode : String,
)
