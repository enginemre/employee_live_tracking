package com.hakmar.employeelivetracking.features.tasks.ui

sealed class TaskDetailEvent {
    data class OnTextChange(val text: String, val fields: TaskDetailFields) : TaskDetailEvent()
    object OnSaveTask : TaskDetailEvent()

}

enum class TaskDetailFields {
    Title,
    Code,
    Description
}
