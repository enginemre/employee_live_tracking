package com.hakmar.employeelivetracking.features.pm_store.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.common.data.remote.dto.StoreDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PmStoreApi {

    @GET("/api/pm-store/{userId}/")
    suspend fun getPmStores(@Path("userId") userId: String): BaseResponseDto<List<StoreDto>>
}