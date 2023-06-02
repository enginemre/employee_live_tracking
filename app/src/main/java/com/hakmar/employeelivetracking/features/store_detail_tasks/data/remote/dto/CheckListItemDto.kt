package com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CheckListItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("task_type")
    val taskType: String,
    @SerializedName("text")
    val text: String
)