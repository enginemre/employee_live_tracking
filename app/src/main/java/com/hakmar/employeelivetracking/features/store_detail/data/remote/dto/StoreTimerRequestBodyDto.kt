package com.hakmar.employeelivetracking.features.store_detail.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StoreTimerRequestBodyDto(
    @SerializedName("store_code")
    var storeCode: String,
    @SerializedName("user_uuid")
    var userId: String,
    @SerializedName("latitude")
    var lat: String,
    @SerializedName("longitude")
    var lon: String
)