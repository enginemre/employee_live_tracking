package com.hakmar.employeelivetracking.features.auth.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.hakmar.employeelivetracking.common.data.remote.dto.ResponseDto

data class LoginResponseDto(
    @SerializedName("data")
    val id: String,
    val response: ResponseDto
)