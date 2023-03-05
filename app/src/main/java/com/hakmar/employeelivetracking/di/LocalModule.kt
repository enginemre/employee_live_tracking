package com.hakmar.employeelivetracking.di

import android.content.Context
import com.hakmar.employeelivetracking.common.data.repository.DataStoreImpl
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreImpl(context = context)
    }
}