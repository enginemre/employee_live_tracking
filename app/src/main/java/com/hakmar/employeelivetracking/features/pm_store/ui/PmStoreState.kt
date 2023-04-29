package com.hakmar.employeelivetracking.features.pm_store.ui

import com.hakmar.employeelivetracking.common.domain.model.Store

data class PmStoreState(
    var isLoading: Boolean = false,
    var pmStoresList: List<Store> = emptyList(),
    var selectedStoreCode: String = "",
)
