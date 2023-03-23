package com.hakmar.employeelivetracking.features.qr_analyze.di

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object QrAnalysisModule {

    @Provides
    @ViewModelScoped
    fun provideBarcodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideScanner(
        @ApplicationContext context: Context,
        options: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, options)
    }
}