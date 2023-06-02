package com.hakmar.employeelivetracking.common.data.mapper

import com.hakmar.employeelivetracking.common.data.remote.dto.DistrictManagerDto
import com.hakmar.employeelivetracking.common.data.remote.dto.MarketingManagerDto
import com.hakmar.employeelivetracking.common.data.remote.dto.ProfileDto
import com.hakmar.employeelivetracking.common.data.remote.dto.RegionalDirectorDto
import com.hakmar.employeelivetracking.common.domain.model.DistirctManager
import com.hakmar.employeelivetracking.common.domain.model.MarketingManager
import com.hakmar.employeelivetracking.common.domain.model.ProfileUser
import com.hakmar.employeelivetracking.common.domain.model.RegionalManager
import com.hakmar.employeelivetracking.features.profile.data.mapper.toUser

fun DistrictManagerDto.toDistirctManager(): DistirctManager {
    return DistirctManager(
        areaCode,
        id,
        marketingManager.toMarketingManager(),
        profile.toProfileUser()
    )
}

fun ProfileDto.toProfileUser(): ProfileUser {
    return ProfileUser(
        phoneNumber ?: "",
        registrationNumber ?: "",
        role ?: "",
        user.toUser(),
        userUuid ?: ""
    )
}

fun RegionalDirectorDto.toRegionalManager(): RegionalManager {
    return RegionalManager(
        areaCode, id, profile.toProfileUser()
    )
}

fun MarketingManagerDto.toMarketingManager(): MarketingManager {
    return MarketingManager(
        areaCode, id, profile.toProfileUser()
    )
}