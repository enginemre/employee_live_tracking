package com.hakmar.employeelivetracking.features.profile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestBody(
    var email: String,
    @SerializedName("user_uuid")
    var userId: String,
    var password: String
)