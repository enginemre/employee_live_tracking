package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("registration_number")
    val registrationNumber: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("user_uuid")
    val userUuid: String
)