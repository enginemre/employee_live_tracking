package com.hakmar.employeelivetracking.features.qr_screen.data

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import javax.inject.Inject

@ExperimentalGetImage
class QrCodeAnalyzer @Inject constructor(
    val onRecived: (String?) -> Unit
) : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        image.image?.let {
            val inputImage = InputImage.fromMediaImage(it, image.imageInfo.rotationDegrees)
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
            val scanner = BarcodeScanning.getClient(options)
            scanner.process(inputImage)
                .addOnSuccessListener { barcode ->
                    if (barcode.isNotEmpty()) {
                        /* val decoded = decodeJwt(barcode.first().rawValue ?: "")
                         decoded?.let { decodedBarcode ->
                             onRecived(decodedBarcode)
                         } ?: onRecived(null)*/
                        // Successfuly tested
                        onRecived(barcode.first().rawValue)

                    }
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                }
                .addOnCompleteListener {
                    image.close()
                }

        }

    }
}