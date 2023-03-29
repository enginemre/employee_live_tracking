package com.hakmar.employeelivetracking.features.auth.data.remote

import com.hakmar.employeelivetracking.features.auth.data.remote.dto.LoginBodyDto
import com.hakmar.employeelivetracking.features.auth.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {


    @POST("/accounts/login/")
    suspend fun login(@Body loginBodyDto: LoginBodyDto): LoginResponseDto
}