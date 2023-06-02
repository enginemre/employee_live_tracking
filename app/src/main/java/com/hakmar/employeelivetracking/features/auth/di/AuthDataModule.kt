package com.hakmar.employeelivetracking.features.auth.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.auth.data.remote.LoginApi
import com.hakmar.employeelivetracking.features.auth.data.repository.LoginRepositoryImpl
import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
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
object AuthDataModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        dataStoreRepository: DataStoreRepository,
        loginApi: LoginApi
    ): LoginRepository {
        return LoginRepositoryImpl(
            dataStore = dataStoreRepository,
            loginApi = loginApi
        )
    }


    @Provides
    @Singleton
    fun provideLoginApi(client: OkHttpClient): LoginApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }
}