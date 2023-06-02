package com.hakmar.employeelivetracking.features.pm_store.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.pm_store.data.remote.PmStoreApi
import com.hakmar.employeelivetracking.features.pm_store.data.repository.PmStoreRepositoryImpl
import com.hakmar.employeelivetracking.features.pm_store.domain.repository.PmStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PmStoreDataModule {

    @Provides
    @Singleton
    fun providesPmStoreRepository(
        pmStoreApi: PmStoreApi,
        dataStoreRepository: DataStoreRepository
    ): PmStoreRepository {
        return PmStoreRepositoryImpl(pmStoreApi, dataStoreRepository)
    }

    @Provides
    @Singleton
    fun provideBsStoreApi(client: OkHttpClient): PmStoreApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }
}