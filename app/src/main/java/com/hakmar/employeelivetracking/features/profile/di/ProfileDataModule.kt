package com.hakmar.employeelivetracking.features.profile.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.profile.data.remote.ProfileApi
import com.hakmar.employeelivetracking.features.profile.data.repository.ProfileRepositoryImpl
import com.hakmar.employeelivetracking.features.profile.domain.repository.ProfileRepository
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
object ProfileDataModule {

    @Provides
    @Singleton
    fun provideProfileRepository(
        api: ProfileApi,
        dataStoreRepository: DataStoreRepository
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            api,
            dataStoreRepository
        )
    }


    @Provides
    @Singleton
    fun provideProfileApi(
        client: OkHttpClient
    ): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }
}