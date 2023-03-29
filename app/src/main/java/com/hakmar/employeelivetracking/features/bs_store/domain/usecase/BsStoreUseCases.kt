package com.hakmar.employeelivetracking.features.bs_store.domain.usecase

import javax.inject.Inject

class BsStoreUseCases @Inject constructor(
    val startGeneralShiftUseCase: StartGeneralShiftUseCase,
    val stopGeneralShiftUseCase: StopGeneralShiftUseCase,
    val getBsStoresUseCase: GetBsStoresUseCase
)