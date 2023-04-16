package com.hakmar.employeelivetracking.features.store_detail.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StoreTimerBodyDto(
    @SerializedName("user_uuid")
    val userId: String,
    val command: String,
    @SerializedName("store_code")
    val storeCode: String
)