package com.hakmar.employeelivetracking.features.tasks.ui.events

import com.hakmar.employeelivetracking.features.tasks.domain.model.Task

sealed class TaskEvent {
    data class OnClickTask(val task: Task) : TaskEvent()
    data class OnLongClickTask(val task: Task) : TaskEvent()

    data class MarkTaskCompleted(val id: Int?) : TaskEvent()

    object DismissDialog : TaskEvent()

    object OnAddTask : TaskEvent()
    object OnRefreshTask : TaskEvent()


}
