package com.hakmar.employeelivetracking.features.auth.di

import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
import com.hakmar.employeelivetracking.features.auth.domain.usecase.AuthUseCases
import com.hakmar.employeelivetracking.features.auth.domain.usecase.LoginUseCase
import com.hakmar.employeelivetracking.features.auth.domain.usecase.UserValidateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthDomainModule {

    @Provides
    fun provideAuthUseCases(
        loginRepository: LoginRepository
    ): AuthUseCases {
        return AuthUseCases(
            loginUseCase = LoginUseCase(loginRepository),
            userValidateUseCase = UserValidateUseCase()
        )
    }
}