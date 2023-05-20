package com.hakmar.employeelivetracking.features.tasks.data.mapper

import androidx.compose.ui.graphics.Color
import com.hakmar.employeelivetracking.features.tasks.data.remote.dto.TaskDto
import com.hakmar.employeelivetracking.features.tasks.data.remote.dto.TaskRequestBodyDto
import com.hakmar.employeelivetracking.features.tasks.domain.model.Task

fun TaskDto.toTask(): Task {
    val randomColor = colorList.random()
    return Task(
        id = id,
        title = title,
        description = description,
        storeCode = store,
        color = randomColor
    )
}

fun Task.toTaskRequestBodyDto(userId: String): TaskRequestBodyDto {
    return TaskRequestBodyDto(
        title = title,
        description = description,
        storeCode = storeCode,
        userId = userId
    )
}

val colorList = listOf<Color>(
    Color.Cyan,
    Color.Yellow,
    Color.Magenta,
    Color.Green,
    Color.LightGray,
)