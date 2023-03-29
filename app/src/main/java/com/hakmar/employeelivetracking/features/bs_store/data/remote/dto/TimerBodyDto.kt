package com.hakmar.employeelivetracking.features.bs_store.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TimerBodyDto(
    val commmand: String,
    @SerializedName("uuid")
    val id: String
)