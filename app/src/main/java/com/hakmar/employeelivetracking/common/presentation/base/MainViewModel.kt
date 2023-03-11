package com.hakmar.employeelivetracking.common.presentation.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.util.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _appBarState = MutableStateFlow(AppBarState())
    val appBarState = _appBarState.asStateFlow()

    fun loginStatus(): Int {
        val isLogin: Int =
            runBlocking { dataStoreRepository.intReadKey(AppConstants.IS_LOGIN) ?: 0 }
        savedStateHandle[AppConstants.IS_LOGIN] = isLogin
        return isLogin
    }


    fun updateAppBar(appBarState: AppBarState) {
        _appBarState.value = appBarState
    }
}