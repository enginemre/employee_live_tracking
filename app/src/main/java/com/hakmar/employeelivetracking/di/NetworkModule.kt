package com.hakmar.employeelivetracking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Named("socket")
    @Provides
    @Singleton
    fun provideSocketClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }


    @Provides
    @Singleton
    fun provideSocketRequest(): Request {
        return Request.Builder()
            .url("ws://64.226.88.88:9001/ws/socket-server/")
            .build()
    }


}