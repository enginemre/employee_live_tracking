package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StoreDto(
    @SerializedName("area_code")
    val areaCode: String,
    @SerializedName("district_manager")
    val districtManager: DistrictManagerDto,
    @SerializedName("id")
    val id: Int,
    @SerializedName("marketing_manager")
    val marketingManager: MarketingManagerDto,
    @SerializedName("regional_director")
    val regionalDirector: RegionalDirectorDto,
    @SerializedName("start_permission")
    val isStoreShiftDisable: Boolean,
    @SerializedName("store_code")
    val storeCode: String,
    @SerializedName("store_latitude")
    val storeLatitude: String,
    @SerializedName("store_longitude")
    val storeLongitude: String,
    @SerializedName("store_name")
    val storeName: String,
    @SerializedName("store_uuid")
    val storeUuid: String,
    @SerializedName("store_email")
    val storeEmail: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("taskCount")
    val taskCount: String,
    @SerializedName("completedTask")
    val completedTask: String?,
    @SerializedName("store_timer_status")
    val storeTimerStatus: String?,
)