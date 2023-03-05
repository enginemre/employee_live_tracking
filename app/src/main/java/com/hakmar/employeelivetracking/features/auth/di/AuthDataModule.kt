package com.hakmar.employeelivetracking.features.auth.di

import com.hakmar.employeelivetracking.features.auth.data.repository.LoginRepositoryImpl
import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }
}