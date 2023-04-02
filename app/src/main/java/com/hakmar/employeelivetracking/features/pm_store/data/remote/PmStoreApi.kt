package com.hakmar.employeelivetracking.features.pm_store.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.features.pm_store.data.remote.dto.PmStoreDto
import retrofit2.http.GET

interface PmStoreApi {

    @GET("/api/pm-store/")
    suspend fun getPmStores(): BaseResponseDto<PmStoreDto>
}