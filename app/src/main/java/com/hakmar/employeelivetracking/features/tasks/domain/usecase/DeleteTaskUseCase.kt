package com.hakmar.employeelivetracking.features.tasks.domain.usecase

import com.hakmar.employeelivetracking.features.tasks.domain.repository.TaskRepository
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(taskId: Int): Flow<Resource<Unit>> {
        return repository.deleteTask(taskId)
    }
}