package com.hakmar.employeelivetracking.features.tasks.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.TaskDestination
import com.hakmar.employeelivetracking.features.tasks.domain.usecase.GetTasksUseCase
import com.hakmar.employeelivetracking.features.tasks.ui.events.TaskEvent
import com.hakmar.employeelivetracking.features.tasks.ui.states.TaskState
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : BaseViewModel<TaskEvent>() {

    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getTasksJob: Job? = null

    init {
        getTasks()
    }


    override fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnClickTask -> {
                goTaskDetail(event.task.id ?: 0)
            }
            TaskEvent.OnAddTask -> {
                goTaskDetail(0)
            }
            TaskEvent.OnRefreshTask -> {
                getTasksWithoutLoading()
            }
        }
    }

    private fun goTaskDetail(id: Int) {
        _uiEvent.trySend(
            UiEvent.Navigate<Int>(
                TaskDestination.TaskDetail.base,
                data = id
            )
        )
    }


    private fun getTasksWithoutLoading() {
        getTasksJob?.cancel()
        getTasksJob = getTasksUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false,
                            taskList = resource.data ?: emptyList()
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isRefreshing = true
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isRefreshing = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = resource.message,
                            type = SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTasks() {
        getTasksJob?.cancel()
        getTasksJob = getTasksUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            taskList = resource.data ?: emptyList()
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            message = resource.message,
                            type = SnackBarType.ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}