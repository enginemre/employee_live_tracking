package com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StoreCheckListRequestBodyDto(
    @SerializedName("answers")
    val answers: List<AnswerDto>,
    @SerializedName("store_code")
    val storeCode: String,
    @SerializedName("user_uuid")
    val userUuid: String
)