package com.hakmar.employeelivetracking.features.bs_store.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import com.hakmar.employeelivetracking.common.data.remote.dto.TimerStatusDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BsStoreApi {

    @GET("/accounts/timer-status/{userId}}/")
    suspend fun initGeneralShift(@Path("userId") userId: String): BaseResponseDto<TimerStatusDto>

    @POST("/accounts/start-timer/")
    suspend fun startGeneralShift(@Body body: TimerRequestBody): BaseResponseDto<TimerDto>

    @GET("/accounts/pause-timer/{userId}/")
    suspend fun pauseGeneralShift(@Path("userId") userId: String): BaseResponseDto<String>

    @GET("/accounts/resume-timer/{userId}/")
    suspend fun resumeGeneralShift(@Path("userId") userId: String): BaseResponseDto<TimerDto>

    @GET("/api/dm-store/{userId}/")
    suspend fun getDmStores(@Path("userId") userId: String): BaseResponseDto<List<StoreDto>>
}