package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class NotifcationDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String?,
    @SerializedName("notified_date")
    val notifiedDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("user_uuid")
    val userUuid: String
)