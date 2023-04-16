package com.hakmar.employeelivetracking.features.qr_screen.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.features.qr_screen.data.QrCodeAnalyzer
import com.hakmar.employeelivetracking.util.AppConstants

@ExperimentalGetImage
class QrActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeLiveTrackingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val cameraProviderFuture = remember {
                        ProcessCameraProvider.getInstance(context)
                    }
                    // Handle camera permission
                    var hasCamPermission by remember {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    }
                    if (!hasCamPermission) {
                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestPermission(),
                            onResult = { granted ->
                                hasCamPermission = granted
                            }
                        )
                        LaunchedEffect(key1 = true) {
                            launcher.launch(Manifest.permission.CAMERA)
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (hasCamPermission) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AndroidView(
                                    factory = { context ->
                                        val previewView = PreviewView(context)
                                        val preview = androidx.camera.core.Preview.Builder().build()
                                        val selector = CameraSelector.Builder()
                                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                            .build()
                                        preview.setSurfaceProvider(previewView.surfaceProvider)
                                        val imageAnalysis = ImageAnalysis.Builder()
                                            .setTargetResolution(
                                                Size(
                                                    previewView.width,
                                                    previewView.height
                                                )
                                            )
                                            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                                            .build()
                                        imageAnalysis.setAnalyzer(
                                            ContextCompat.getMainExecutor(context),
                                            QrCodeAnalyzer {
                                                finshResult(it)
                                            }
                                        )
                                        try {
                                            cameraProviderFuture.get().bindToLifecycle(
                                                lifecycleOwner,
                                                selector,
                                                preview,
                                                imageAnalysis
                                            )
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                        previewView
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun finshResult(qrData: String?) {
        qrData?.let {
            val resultIntent = Intent().apply {
                putExtra(AppConstants.QR_DATA, qrData)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        } ?: kotlin.run {
            setResult(RESULT_CANCELED)
            finish()
        }


    }
}

