package com.hakmar.employeelivetracking.features.tasks.domain.repository

import com.hakmar.employeelivetracking.features.tasks.domain.model.Task
import com.hakmar.employeelivetracking.util.Resource
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun updateTask(task: Task): Flow<Resource<Task>>

    fun createTask(task: Task): Flow<Resource<Unit>>

    fun deleteTask(taskId: Int): Flow<Resource<Unit>>

    fun getTasks(): Flow<Resource<List<Task>>>

    fun getTaskById(taskId: Int): Flow<Resource<Task>>
}