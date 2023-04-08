package com.hakmar.employeelivetracking.features.tasks.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.tasks.data.mapper.toTask
import com.hakmar.employeelivetracking.features.tasks.data.mapper.toTaskRequestBodyDto
import com.hakmar.employeelivetracking.features.tasks.data.remote.TasksApi
import com.hakmar.employeelivetracking.features.tasks.domain.model.Task
import com.hakmar.employeelivetracking.features.tasks.domain.repository.TaskRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException

class TaskRepositoryImpl(
    private val tasksApi: TasksApi,
    private val dataStoreRepository: DataStoreRepository
) : TaskRepository {
    override fun updateTask(task: Task): Flow<Resource<Task>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = tasksApi.updateTask(task.id.toString(), task.toTaskRequestBodyDto())
                if (result.response.success) {
                    emit(Resource.Success(task))
                } else {
                    emit(
                        Resource.Error(
                            message = UiText.DynamicString(result.response.message)
                        )
                    )
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun createTask(task: Task): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = tasksApi.createTask(
                    task.toTaskRequestBodyDto()
                )
                if (result.response.success) {
                    emit(Resource.Success(Unit))
                } else {
                    emit(
                        Resource.Error(
                            message = UiText.DynamicString(result.response.message)
                        )
                    )
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun deleteTask(taskId: Int): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = tasksApi.deleteTask(taskId.toString())
                if (result.response.success) {
                    emit(Resource.Success(Unit))
                } else {
                    emit(
                        Resource.Error(
                            message = UiText.DynamicString(result.response.message)
                        )
                    )
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }

        }
    }

    override fun getTasks(): Flow<Resource<List<Task>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    val result = tasksApi.getTasks(it)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.map { taskDto -> taskDto.toTask() }))
                    } else {
                        emit(
                            Resource.Error(
                                message = UiText.DynamicString(result.response.message)
                            )
                        )
                    }
                } ?: kotlin.run {
                    emit(
                        Resource.Error(
                            message = UiText.StringResorce(R.string.error_user_info)
                        )
                    )
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun getTaskById(taskId: Int): Flow<Resource<Task>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = tasksApi.getTask(taskId.toString())
                if (result.response.success) {
                    emit(Resource.Success(result.data.toTask()))
                } else {
                    emit(
                        Resource.Error(
                            message = UiText.DynamicString(result.response.message)
                        )
                    )
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }


        }
    }


}