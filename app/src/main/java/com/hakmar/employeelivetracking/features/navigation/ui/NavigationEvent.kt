package com.hakmar.employeelivetracking.features.navigation.ui

import com.hakmar.employeelivetracking.features.navigation.domain.model.NavigationStore

sealed class NavigationEvent {
    data class OnStoreClick(val data: NavigationStore) : NavigationEvent()
}
