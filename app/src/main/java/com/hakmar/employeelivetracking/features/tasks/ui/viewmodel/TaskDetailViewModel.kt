package com.hakmar.employeelivetracking.features.tasks.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.tasks.ui.TaskDetailEvent
import com.hakmar.employeelivetracking.features.tasks.ui.TaskDetailFields
import com.hakmar.employeelivetracking.features.tasks.ui.TaskDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskDetailEvent>() {

    private val _state = MutableStateFlow(TaskDetailState())
    val state = _state.asStateFlow()
    private var taskId: String? = null

    init {
        taskId = savedStateHandle["taskId"]
    }

    override fun onEvent(event: TaskDetailEvent) {
        when (event) {
            is TaskDetailEvent.OnTextChange -> {
                when (event.fields) {
                    TaskDetailFields.Title -> {
                        _state.update {
                            it.copy(
                                title = event.text
                            )
                        }
                    }
                    TaskDetailFields.Code -> {
                        _state.update {
                            it.copy(
                                code = event.text
                            )
                        }
                    }
                    TaskDetailFields.Description -> {
                        _state.update {
                            it.copy(
                                description = event.text
                            )
                        }
                    }
                }
            }
            TaskDetailEvent.OnSaveTask -> {
                saveTask(state.value.title, state.value.code, state.value.description)
            }
        }
    }

    private fun getTaskDetail() {
        taskId?.let {
            if (taskId == "new") {
                Log.i("Fener", it)
            }
        } ?: kotlin.run {

        }
    }

    private fun saveTask(title: String, code: String, description: String) {

    }


}