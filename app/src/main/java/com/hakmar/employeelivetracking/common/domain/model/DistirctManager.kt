package com.hakmar.employeelivetracking.common.domain.model

data class DistirctManager(
    val areaCode: String?,
    val id: Int,
    val marketingManagerId: MarketingManager,
    val profile: ProfileUser
)