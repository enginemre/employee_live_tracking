package com.hakmar.employeelivetracking.features.qr_analyze.domain.repository

import kotlinx.coroutines.flow.Flow

interface QrAnalysisRepository {

    fun startScanning(): Flow<String>
}