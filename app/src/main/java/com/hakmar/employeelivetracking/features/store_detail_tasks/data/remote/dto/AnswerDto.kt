package com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AnswerDto(
    @SerializedName("answer")
    val answer: Boolean,
    @SerializedName("question")
    val question: Int
)