package com.hakmar.employeelivetracking.features.bs_store.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StoreItemDto(
    @SerializedName("area_code")
    val areaCode: String,
    @SerializedName("district_manager")
    val districtManager: String,
    val id: Int,
    @SerializedName("marketing_manager")
    val marketingManager: String,
    @SerializedName("regional_director")
    val regionalDirector: String,
    @SerializedName("store_code")
    val storeCode: String,
    @SerializedName("store_latitude")
    val storeLatitude: String,
    @SerializedName("store_longitude")
    val storeLongitude: String,
    @SerializedName("store_name")
    val storeName: String,
    @SerializedName("store_uuid")
    val storeUuid: String
)