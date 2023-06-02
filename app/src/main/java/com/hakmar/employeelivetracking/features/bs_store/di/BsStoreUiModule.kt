package com.hakmar.employeelivetracking.features.bs_store.di

import com.hakmar.employeelivetracking.common.domain.usecases.CalculateDistanceUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.BsStoreUseCases
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.GetBsStoresUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.GetGeneralShiftStatusUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.PauseGeneralShiftUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.ResumeGeneralShiftUseCase
import com.hakmar.employeelivetracking.features.bs_store.domain.usecase.StartGeneralShiftUseCase
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
       pauseGeneralShiftUseCase = PauseGeneralShiftUseCase(bsStoreRepository),
       resumeGeneralShiftUseCase = ResumeGeneralShiftUseCase(bsStoreRepository),
       getBsStoresUseCase = GetBsStoresUseCase(bsStoreRepository),
       getGeneralShiftStatusUseCase = GetGeneralShiftStatusUseCase(bsStoreRepository),
       calculateDistanceUseCase = CalculateDistanceUseCase()
   )
}