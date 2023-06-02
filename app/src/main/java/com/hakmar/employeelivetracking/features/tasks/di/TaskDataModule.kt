package com.hakmar.employeelivetracking.features.tasks.di

import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.tasks.data.remote.TasksApi
import com.hakmar.employeelivetracking.features.tasks.data.repository.TaskRepositoryImpl
import com.hakmar.employeelivetracking.features.tasks.domain.repository.TaskRepository
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
object TaskDataModule {

    @Provides
    @Singleton
    fun provideTaskApi(
        client: OkHttpClient
    ): TasksApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TasksApi,
        dataStoreRepository: DataStoreRepository
    ): TaskRepository {
        return TaskRepositoryImpl(api, dataStoreRepository)
    }
}