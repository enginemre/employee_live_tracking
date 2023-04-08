package com.hakmar.employeelivetracking.features.tasks.ui.events

import com.hakmar.employeelivetracking.features.tasks.domain.model.Task

sealed class TaskEvent {
    data class OnClickTask(val task: Task) : TaskEvent()
    object OnAddTask : TaskEvent()
    object OnRefreshTask : TaskEvent()
}
