package com.hakmar.employeelivetracking.features.profile.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserDto(
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
)