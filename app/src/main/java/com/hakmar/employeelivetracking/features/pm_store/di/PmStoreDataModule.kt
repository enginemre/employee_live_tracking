package com.hakmar.employeelivetracking.features.pm_store.di

import com.hakmar.employeelivetracking.features.pm_store.data.repository.PmStoreRepositoryImpl
import com.hakmar.employeelivetracking.features.pm_store.domain.repository.PmStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PmStoreDataModule {

    @Provides
    @Singleton
    fun providesPmStoreRepository(): PmStoreRepository {
        return PmStoreRepositoryImpl()
    }
}