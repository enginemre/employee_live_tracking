package com.hakmar.employeelivetracking.features.pm_store.ui

import com.hakmar.employeelivetracking.features.pm_store.domain.model.PmStore

data class PmStoreState(
    var isLoading: Boolean = false,
    var pmStoresList: List<PmStore> = emptyList()
)
