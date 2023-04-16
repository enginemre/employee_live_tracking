package com.hakmar.employeelivetracking.features.store_detail.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import com.hakmar.employeelivetracking.common.data.remote.dto.TimerStatusDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreDetailApi {

    @GET("/api/store-detail/")
    suspend fun getStoreDetail(
        @Query("store_code") storeCode: String,
        @Query("user_uuid") userId: String
    ): BaseResponseDto<StoreDto>

    @GET("api/start-timer/")
    suspend fun startStoreShift(
        @Query("store_code") storeCode: String,
        @Query("user_uuid") userId: String
    ): BaseResponseDto<TimerDto>

    @GET("/api/pause-timer/")
    suspend fun pauseStoreShift(
        @Query("store_code") storeCode: String,
        @Query("user_uuid") userId: String
    ): BaseResponseDto<String>

    @GET("api/resume-timer/")
    suspend fun resumeStoreShift(
        @Query("store_code") storeCode: String,
        @Query("user_uuid") userId: String
    ): BaseResponseDto<TimerDto>

    @GET("api/stop-timer/")
    suspend fun cancelStoreShift(
        @Query("store_code") storeCode: String,
        @Query("user_uuid") userId: String
    ): BaseResponseDto<String>

    @GET("/api/timer-status/")
    suspend fun initStoreShift(
        @Query("store_code") storeCode: String,
        @Query("user_uuid") userId: String
    ): BaseResponseDto<TimerStatusDto>
}