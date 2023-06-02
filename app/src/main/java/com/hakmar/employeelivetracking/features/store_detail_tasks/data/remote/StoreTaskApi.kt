package com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote

import com.hakmar.employeelivetracking.common.data.remote.dto.BaseResponseDto
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto.CheckListItemDto
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto.StoreCheckListRequestBodyDto
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface StoreTaskApi {

    @POST("/api/forms/steel-safe-amount/")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun sendSteelCaseAmount(@FieldMap params: Map<String, Any>): BaseResponseDto<String>

    @POST("/api/forms/pos-amount/")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun sendPostAmount(@FieldMap params: Map<String, Any>): BaseResponseDto<String>

    @GET("/api/forms/store-opening/")
    suspend fun getStoreInsideCheckList(): BaseResponseDto<List<CheckListItemDto>>

    @GET("/api/forms/store-closing/")
    suspend fun getStoreOutsideCheckList(): BaseResponseDto<List<CheckListItemDto>>

    @POST("/api/forms/store-opening/")
    suspend fun sendStoreInsideCheckList(@Body body: StoreCheckListRequestBodyDto): BaseResponseDto<String>

    @POST("/api/forms/store-closing/")
    suspend fun sendStoreOutSideCheckList(@Body body: StoreCheckListRequestBodyDto): BaseResponseDto<String>


}