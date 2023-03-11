package com.hakmar.employeelivetracking.features.pm_store.domain.model

data class PmStore(
    val id: Int,
    val storeCode: String,
    val storeName: String,
    val pmId: Int,
    val bsId: Int
)
