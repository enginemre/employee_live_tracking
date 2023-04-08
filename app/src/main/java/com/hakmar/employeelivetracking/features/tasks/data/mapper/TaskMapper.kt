package com.hakmar.employeelivetracking.features.tasks.data.mapper

import androidx.compose.ui.graphics.Color
import com.hakmar.employeelivetracking.features.tasks.data.remote.dto.TaskDto
import com.hakmar.employeelivetracking.features.tasks.data.remote.dto.TaskRequestBodyDto
import com.hakmar.employeelivetracking.features.tasks.domain.model.Task
import kotlin.random.Random

fun TaskDto.toTask(): Task {
    val randomColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    return Task(
        id = id,
        title = title,
        description = description,
        storeCode = store,
        color = randomColor
    )
}

fun Task.toTaskRequestBodyDto(): TaskRequestBodyDto {
    return TaskRequestBodyDto(
        title = title,
        description = description,
        storeCode = storeCode
    )
}