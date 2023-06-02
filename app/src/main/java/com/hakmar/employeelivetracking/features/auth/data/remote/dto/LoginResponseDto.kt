package com.hakmar.employeelivetracking.features.auth.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("user_uuid")
    val id: String,
    @SerializedName("district_manager")
    val districtManager: Boolean,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("marketing_manager")
    val marketingManager: Boolean,
    @SerializedName("regional_director")
    val regionalDirector: Boolean,
    @SerializedName("username")
    val username: String
)