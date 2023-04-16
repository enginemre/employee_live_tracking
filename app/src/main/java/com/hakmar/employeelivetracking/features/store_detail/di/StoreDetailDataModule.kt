package com.hakmar.employeelivetracking.features.store_detail.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.store_detail.data.remote.StoreDetailApi
import com.hakmar.employeelivetracking.features.store_detail.data.repository.StoreDetailRepositoryImpl
import com.hakmar.employeelivetracking.features.store_detail.domain.repository.StoreDetailRepository
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
object StoreDetailDataModule {

    @Provides
    @Singleton
    fun provideStoreDetailApi(
        client: OkHttpClient
    ): StoreDetailApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()

    }

    @Provides
    @Singleton
    fun provideStoreDetailRepository(
        storeDetailApi: StoreDetailApi,
        dataStoreRepository: DataStoreRepository
    ): StoreDetailRepository {
        return StoreDetailRepositoryImpl(storeDetailApi, dataStoreRepository)
    }
}