package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MarketingManagerDto(
    @SerializedName("area_code")
    val areaCode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("profile")
    val profile: ProfileDto
)