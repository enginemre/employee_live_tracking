package com.hakmar.employeelivetracking.features.qr_analyze.data.repository

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.hakmar.employeelivetracking.features.qr_analyze.domain.repository.QrAnalysisRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class QrAnalysisRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner
) : QrAnalysisRepository {
    override fun startScanning(): Flow<String> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    launch {
                        send(barcode.rawValue ?: "")
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose {}
        }
    }

    private fun getDetails(barcode: Barcode): String? {
        return barcode.rawValue
    }
}