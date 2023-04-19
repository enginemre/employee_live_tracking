package com.hakmar.employeelivetracking.features.store_detail_tasks.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.StoreTaskApi
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.repository.PosAmountRepositoryImpl
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.repository.SteelCaseRepositoryImpl
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.repository.StoreInsideRepositoryImpl
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.repository.StoreOutsideRepositoryImpl
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.PosAmountRepository
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.SteelCaseRepository
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.StoreInsideRepository
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.StoreOutsideRepository
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
object StoreDetailTaskDataModule {


    @Provides
    @Singleton
    fun provideStoreTaskApi(
        okHttpClient: OkHttpClient
    ): StoreTaskApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSteelCaseRepository(
        dataStoreRepository: DataStoreRepository,
        storeTaskApi: StoreTaskApi
    ): SteelCaseRepository {
        return SteelCaseRepositoryImpl(storeTaskApi, dataStoreRepository)
    }

    @Provides
    @Singleton
    fun providePosAmountRepository(
        dataStoreRepository: DataStoreRepository,
        storeTaskApi: StoreTaskApi
    ): PosAmountRepository {
        return PosAmountRepositoryImpl(dataStoreRepository, storeTaskApi)
    }

    @Provides
    @Singleton
    fun provideStoreInsideRepository(
        dataStoreRepository: DataStoreRepository,
        storeTaskApi: StoreTaskApi
    ): StoreInsideRepository {
        return StoreInsideRepositoryImpl(dataStoreRepository, storeTaskApi)
    }

    @Provides
    @Singleton
    fun provideStoreOutsideRepository(
        dataStoreRepository: DataStoreRepository,
        storeTaskApi: StoreTaskApi
    ): StoreOutsideRepository {
        return StoreOutsideRepositoryImpl(dataStoreRepository, storeTaskApi)
    }
}