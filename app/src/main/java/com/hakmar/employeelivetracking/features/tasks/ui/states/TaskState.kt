package com.hakmar.employeelivetracking.features.tasks.ui.states

import com.hakmar.employeelivetracking.features.tasks.domain.model.Task

data class TaskState(
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    var taskList: List<Task> = emptyList(),
    var shouldShowDialog: Boolean = false,
    var selectedTaskId: Int? = 0
)
