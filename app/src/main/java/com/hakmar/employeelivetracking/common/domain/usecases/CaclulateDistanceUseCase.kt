package com.hakmar.employeelivetracking.common.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import javax.inject.Inject

class CaclulateDistanceUseCase @Inject constructor() {

    companion object {
        private const val DISTANCE_LIMIT = 220
    }

    operator fun invoke(
        currentLat: Double,
        currentLon: Double,
        targetLat: Double,
        targetLon: Double
    ): ResultDistance {
        val distance = SphericalUtil.computeDistanceBetween(
            LatLng(currentLat, currentLon),
            LatLng(targetLat, targetLon)
        )
        return if (distance > DISTANCE_LIMIT) {
            ResultDistance.FarAway
        } else {
            ResultDistance.Near
        }
    }

    sealed class ResultDistance {
        object Near : ResultDistance()

        object FarAway : ResultDistance()
    }
}

