package com.hakmar.employeelivetracking.features.bs_store.di

import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.BsStoreUseCases
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.GetBsStoresUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.StartGeneralShiftUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.StopGeneralShiftUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object BsStoreUiModule {

   @Provides
   fun provideBsStoreUsecases(
       bsStoreRepository: BsStoreRepository
   ) = BsStoreUseCases(
       startGeneralShiftUseCase = StartGeneralShiftUseCase(bsStoreRepository),
       stopGeneralShiftUseCase = StopGeneralShiftUseCase(bsStoreRepository),
       getBsStoresUseCase = GetBsStoresUseCase(bsStoreRepository)
   )
}