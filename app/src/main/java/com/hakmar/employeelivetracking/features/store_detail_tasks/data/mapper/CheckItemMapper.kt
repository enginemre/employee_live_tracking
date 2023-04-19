package com.hakmar.employeelivetracking.features.store_detail_tasks.data.mapper

import com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto.AnswerDto
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem


fun CheckItem.toAnswerDto() = AnswerDto(
    answer = completed,
    question = id
)