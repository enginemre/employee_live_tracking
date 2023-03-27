package com.hakmar.employeelivetracking.features.auth.data.remote

import retrofit2.http.POST

interface LoginApi {


    @POST("/bla")
    suspend fun login()
}