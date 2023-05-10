package com.hakmar.employeelivetracking.features.bs_store.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TimerRequestBody(
    @SerializedName("user_uuid")
    var userId: String,
    @SerializedName("latitude")
    var lat: String,
    @SerializedName("longitude")
    var lon: String
)