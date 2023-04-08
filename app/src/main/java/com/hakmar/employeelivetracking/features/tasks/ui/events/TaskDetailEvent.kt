package com.hakmar.employeelivetracking.features.tasks.ui.events

sealed class TaskDetailEvent {
    data class OnTextChange(val text: String, val fields: TaskDetailFields) : TaskDetailEvent()
    object OnSaveTask : TaskDetailEvent()
    object OnDeleteTask : TaskDetailEvent()

}

enum class TaskDetailFields {
    Title,
    Code,
    Description
}
