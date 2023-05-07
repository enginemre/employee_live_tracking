package com.hakmar.employeelivetracking.features.tasks.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.features.tasks.data.remote.dto.TaskDto
import com.hakmar.employeelivetracking.features.tasks.data.remote.dto.TaskRequestBodyDto
import retrofit2.http.*

interface TasksApi {

    @GET("/api/task/{userId}")
    suspend fun getTasks(@Path("userId") userId: String): BaseResponseDto<List<TaskDto>>

    @GET("/api/task-detail/{taskId}")
    suspend fun getTask(@Path("taskId") taskId: String): BaseResponseDto<TaskDto>

    @PUT("/api/task/{taskId}/")
    suspend fun updateTask(
        @Path("taskId") taskId: String,
        @Body() taskBody: TaskRequestBodyDto
    ): BaseResponseDto<String>

    @DELETE("/api/task/{taskId}/")
    suspend fun deleteTask(@Path("taskId") taskId: String): BaseResponseDto<String>

    @GET("/api/complete-task/{taskId}/")
    suspend fun markCompleted(@Path("taskId") taskId: String): BaseResponseDto<String?>

    @POST("/api/task/1/")
    suspend fun createTask(@Body taskBody: TaskRequestBodyDto): BaseResponseDto<String>
}