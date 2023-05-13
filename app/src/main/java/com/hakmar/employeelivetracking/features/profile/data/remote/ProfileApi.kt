package com.hakmar.employeelivetracking.features.profile.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.common.data.remote.dto.UserDto
import com.hakmar.employeelivetracking.features.profile.data.remote.dto.ChangePasswordRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {

    @GET("/accounts/user-detail/{userId}")
    suspend fun getUserDetail(@Path("userId") userId: String): BaseResponseDto<UserDto>

    @POST("/accounts/reset-password/")
    suspend fun changePassword(@Body changePasswordRequestBody: ChangePasswordRequestBody): BaseResponseDto<String>
}