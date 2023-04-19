package com.hakmar.employeelivetracking.common.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.common.data.remote.dto.NotifcationDto
import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GeneralApi {

    @GET("/api/store/{userId}/")
    suspend fun getAllStores(@Path("userId") userId: String): BaseResponseDto<List<StoreDto>>

    @GET("/notifications/{userId}/")
    suspend fun getNotifications(@Path("userId") userId: String): BaseResponseDto<List<NotifcationDto>>
}