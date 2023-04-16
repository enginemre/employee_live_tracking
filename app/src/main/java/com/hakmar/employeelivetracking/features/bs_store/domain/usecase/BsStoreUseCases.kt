package com.hakmar.employeelivetracking.features.bs_store.domain.usecase

import javax.inject.Inject

class BsStoreUseCases @Inject constructor(
    val startGeneralShiftUseCase: StartGeneralShiftUseCase,
    val pauseGeneralShiftUseCase: PauseGeneralShiftUseCase,
    val resumeGeneralShiftUseCase: ResumeGeneralShiftUseCase,
    val getBsStoresUseCase: GetBsStoresUseCase,
    val getGeneralShiftStatusUseCase: GetGeneralShiftStatusUseCase
)