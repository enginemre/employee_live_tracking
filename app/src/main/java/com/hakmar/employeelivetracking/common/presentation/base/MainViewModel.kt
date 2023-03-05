package com.hakmar.employeelivetracking.common.presentation.base

import androidx.lifecycle.ViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _appBarState = MutableStateFlow(AppBarState())
    val appBarState = _appBarState.asStateFlow()


    fun updateAppBar(appBarState: AppBarState) {
        _appBarState.value = appBarState
    }
}