package com.hakmar.employeelivetracking.features.bs_store.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.bs_store.data.remote.BsStoreApi
import com.hakmar.employeelivetracking.features.bs_store.data.repository.BsStoreRepositoryImpl
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
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
object BsStoreDataModule {

    @Provides
    @Singleton
    fun provideBsStoreRepository(
        bsStoreApi: BsStoreApi,
        dataStoreRepository: DataStoreRepository
    ): BsStoreRepository {
        return BsStoreRepositoryImpl(bsStoreApi, dataStoreRepository)
    }


    @Provides
    @Singleton
    fun provideBsStoreApi(client: OkHttpClient): BsStoreApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }
}