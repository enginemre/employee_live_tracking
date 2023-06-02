package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class DistrictManagerDto(
    @SerializedName("area_code")
    val areaCode: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("marketing_manager")
    val marketingManager: MarketingManagerDto,
    @SerializedName("profile")
    val profile: ProfileDto,
)