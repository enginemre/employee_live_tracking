package com.hakmar.employeelivetracking.features.tasks.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TaskRequestBodyDto(
    val description: String,
    @SerializedName("store_code")
    val storeCode: String,
    val title: String
)