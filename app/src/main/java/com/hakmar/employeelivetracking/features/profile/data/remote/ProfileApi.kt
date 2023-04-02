package com.hakmar.employeelivetracking.features.profile.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.features.profile.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET("/accounts/user-detail/{userId}")
    suspend fun getUserDetail(@Path("userId") userId: String): BaseResponseDto<UserDto>
}