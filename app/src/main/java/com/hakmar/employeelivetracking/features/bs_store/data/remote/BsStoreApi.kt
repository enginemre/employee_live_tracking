package com.hakmar.employeelivetracking.features.bs_store.data.remote

import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.AllStoreDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TempTimerDto
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerBodyDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BsStoreApi {

    @GET("/accounts/start-timer/{userId}")
    suspend fun startGeneralShift(@Path("userId") userId: String) : TempTimerDto

    @POST("/accounts/stop-timer/")
    suspend fun stopGeneralShift(@Body body: TimerBodyDto)

    @GET("/api/store/")
    suspend fun getAllStores() : AllStoreDto
}