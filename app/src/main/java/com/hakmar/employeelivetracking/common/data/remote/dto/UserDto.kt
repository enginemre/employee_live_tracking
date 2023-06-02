package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
)