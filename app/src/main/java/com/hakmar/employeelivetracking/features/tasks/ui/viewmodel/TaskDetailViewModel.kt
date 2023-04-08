package com.hakmar.employeelivetracking.features.tasks.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.features.tasks.domain.model.Task
import com.hakmar.employeelivetracking.features.tasks.domain.usecase.CreateTaskUseCase
import com.hakmar.employeelivetracking.features.tasks.domain.usecase.DeleteTaskUseCase
import com.hakmar.employeelivetracking.features.tasks.domain.usecase.GetTaskByIdUseCase
import com.hakmar.employeelivetracking.features.tasks.domain.usecase.UpdateTaskUseCase
import com.hakmar.employeelivetracking.features.tasks.ui.events.TaskDetailEvent
import com.hakmar.employeelivetracking.features.tasks.ui.events.TaskDetailFields
import com.hakmar.employeelivetracking.features.tasks.ui.states.TaskDetailState
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val saveTaskUseCase: UpdateTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : BaseViewModel<TaskDetailEvent>() {

    private val _state = MutableStateFlow(TaskDetailState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var saveTaskJob: Job? = null
    private var getTaskById: Job? = null
    private var createTaskById: Job? = null
    private var deleteTaskById: Job? = null


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
                if (state.value.task != null)
                    saveTask(state.value.title, state.value.code, state.value.description)
                else
                    createTask()
            }
            TaskDetailEvent.OnDeleteTask -> {
                deleteTask()
            }
        }
    }


    fun getTask(id: Int?) {
        id?.let { id ->
            if (id != 0) {
                getTaskById?.cancel()
                getTaskById = getTaskByIdUseCase(id).onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    task = resource.data!!,
                                    title = resource.data.title,
                                    description = resource.data.description,
                                    code = resource.data.storeCode
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
                                    isLoading = false
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _state.update {
                    it.copy(
                        task = null,
                        title = "",
                        description = "",
                        code = ""
                    )
                }
            }
        }
    }

    private fun saveTask(title: String, code: String, description: String) {
        val task = state.value.task?.apply {
            this.description = description
            this.storeCode = code
            this.title = title
        } ?: Task(
            null,
            title = title,
            storeCode = code,
            description = description,
            color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        )
        saveTaskJob?.cancel()
        saveTaskJob = saveTaskUseCase(task).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.Navigate<Boolean>(
                            data = true,
                            route = HomeDestination.Tasks.base
                        )
                    )
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
                            isLoading = false
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

    private fun createTask() {
        createTaskById?.cancel()
        createTaskById = createTaskUseCase(
            Task(
                null,
                color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
                title = state.value.title,
                description = state.value.description,
                storeCode = state.value.code
            )
        ).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = HomeDestination.Tasks.base,
                            data = null
                        )
                    )
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
                            isLoading = false
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

    private fun deleteTask() {
        deleteTaskById?.cancel()
        deleteTaskById = deleteTaskUseCase(state.value.task!!.id!!).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.Navigate(
                            data = null,
                            route = HomeDestination.Tasks.base
                        )
                    )
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
                            isLoading = false
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