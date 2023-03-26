package com.hakmar.employeelivetracking.features.qr_analyze.di

import com.hakmar.employeelivetracking.features.qr_analyze.data.repository.QrAnalysisRepositoryImpl
import com.hakmar.employeelivetracking.features.qr_analyze.domain.repository.QrAnalysisRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindQRRepository(
        qrAnalysisRepositoryImpl: QrAnalysisRepositoryImpl
    ): QrAnalysisRepository
}