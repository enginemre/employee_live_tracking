package com.hakmar.employeelivetracking.features.tasks.ui.states

import com.hakmar.employeelivetracking.features.tasks.domain.model.Task

data class TaskDetailState(
    var isLoading: Boolean = false,
    var title: String = "",
    var code: String = "",
    var description: String = "",
    var task: Task? = null
)
