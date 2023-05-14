package com.hakmar.employeelivetracking.common.data.remote.dto


import com.google.gson.annotations.SerializedName
// "pos_amount_completed":false,"steel_safe_completed":false,"store_opening_completed":false,"store_closing_completed":false
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
    val taskCount: String?,
    @SerializedName("completedTask")
    val completedTask: String?,
    @SerializedName("store_timer_status")
    val storeTimerStatus: String?,
    @SerializedName("pos_amount_completed")
    val postAmountStatus: Boolean?,
    @SerializedName("store_closing_completed")
    val storeClosingStatus: Boolean?,
    @SerializedName("store_opening_completed")
    val storeOpeningStatus: Boolean?,
    @SerializedName("steel_safe_completed")
    val steelCaseStatus: Boolean?,

    )