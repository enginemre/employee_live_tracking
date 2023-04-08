package com.hakmar.employeelivetracking.features.bs_store.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.StoreItemDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerBodyDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BsStoreApi {

    @GET("/accounts/start-timer/{userId}")
    suspend fun startGeneralShift(@Path("userId") userId: String): BaseResponseDto<TimerDto>

    @POST("/accounts/stop-timer/")
    suspend fun stopGeneralShift(@Body body: TimerBodyDto): BaseResponseDto<String>

    @GET("/api/store/")
    suspend fun getAllStores(): BaseResponseDto<List<StoreItemDto>>
}