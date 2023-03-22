package com.hakmar.employeelivetracking.features.tasks.ui

data class TaskDetailState(
    var isLoading: Boolean = false,
    var title: String = "",
    var code: String = "",
    var description: String = ""
)
