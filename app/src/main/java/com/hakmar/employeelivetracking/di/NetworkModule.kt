package com.hakmar.employeelivetracking.di

import com.hakmar.employeelivetracking.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(Duration.ofSeconds(AppConstants.TIMEOUT_SHORT))
            .readTimeout(Duration.ofSeconds(AppConstants.TIMEOUT_LONG))
            .build()
    }


}