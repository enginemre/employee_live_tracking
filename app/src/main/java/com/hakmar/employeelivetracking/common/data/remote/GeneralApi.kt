package com.hakmar.employeelivetracking.common.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.StoreResponseDto
import retrofit2.http.GET

interface GeneralApi {

    @GET("/api/store")
    suspend fun getAllStores(): StoreResponseDto
}