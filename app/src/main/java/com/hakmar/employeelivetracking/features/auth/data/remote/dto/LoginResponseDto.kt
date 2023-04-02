package com.hakmar.employeelivetracking.features.auth.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("user_uuid")
    val id: String,
)