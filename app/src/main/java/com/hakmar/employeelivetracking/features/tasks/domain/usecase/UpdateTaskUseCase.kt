package com.hakmar.employeelivetracking.features.tasks.domain.usecase

import com.hakmar.employeelivetracking.features.tasks.domain.model.Task
import com.hakmar.employeelivetracking.features.tasks.domain.repository.TaskRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(task: Task): Flow<Resource<Task>> {
        return repository.updateTask(task)
    }
}